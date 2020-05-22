package org.corant.demo.ddd;

import static org.corant.shared.util.MapUtils.mapOf;
import static org.corant.suites.cdi.Instances.resolve;
import org.corant.demo.ddd.application.commad.UserService;
import org.junit.Test;

public class UserServiceTest extends AbstractTest {

  @Test
  public void create() {
    resolve(UserService.class).create(mapOf("name", "bingo"));
  }

  @Test
  public void createAndSendMessage() {
    resolve(UserService.class).create(mapOf("name", "bingo-issueMessage1", "issueMessage", true));
  }

  @Test
  public void delete() {
    resolve(UserService.class).delete(mapOf("id", "535278628414423040"));
  }

  @Test
  public void update() {
    resolve(UserService.class).update(mapOf("id", "535285121197015040", "name", "bingo"));
  }

}
