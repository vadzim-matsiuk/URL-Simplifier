package simplifier;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import simplifier.model.User;
import simplifier.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class UserControllerIT {

  private TestRestTemplate restTemplate = new TestRestTemplate();

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setRestTemplate(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Test
  public void createUser() {
   User user = new User();
   user.setUsername("test");
   user.setPassword("test");

   ResponseEntity<User> responseEntity = restTemplate.postForEntity("/user",
       user, User.class);

   User savedUser = userRepository.findByUsername("test");

   assertThat(savedUser, is(user));
   assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
   assertThat(responseEntity.getBody(), is(nullValue()));
  }
}
