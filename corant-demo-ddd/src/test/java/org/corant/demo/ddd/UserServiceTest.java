package org.corant.demo.ddd;

import static io.restassured.RestAssured.given;
import static org.corant.shared.util.MapUtils.mapOf;
import javax.ws.rs.core.MediaType;
import org.junit.Test;

public class UserServiceTest extends AbstractTest {

  @Test
  public void create() {
    given().body(mapOf("name", "xxx")).contentType(MediaType.APPLICATION_JSON).when()
        .post("ddd/users/save/").prettyPrint();
  }

  @Test
  public void createAndSendMessage() {
    given().body(mapOf("name", "jimmy", "notifyCreated", true))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/users/save/").prettyPrint();

  }

  @Test
  public void delete() {
    given().body(mapOf("id", "582008048755671040")).contentType(MediaType.APPLICATION_JSON).when()
        .post("ddd/users/delete/").prettyPrint();
  }
}
