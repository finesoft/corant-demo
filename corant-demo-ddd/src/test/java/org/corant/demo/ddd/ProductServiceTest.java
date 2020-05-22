package org.corant.demo.ddd;

import static io.restassured.RestAssured.given;
import static org.corant.shared.util.CollectionUtils.listOf;
import static org.corant.shared.util.MapUtils.mapOf;
import javax.ws.rs.core.MediaType;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.junit.Test;

public class ProductServiceTest extends AbstractTest {

  @Test
  public void create() {
    given()
        .body(mapOf("number", "827001.0002", "name", "Brake disc", "remark", "Brake discs",
            "attributes",
            listOf(mapOf("name", "height", "type", AttributeType.NUMERIC, "value", "60"),
                mapOf("name", "diameter ", "type", AttributeType.NUMERIC, "value", "285"),
                mapOf("name", "Bolt hole number", "type", AttributeType.NUMERIC, "value", "5"))))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/products/save/").prettyPrint();
  }

  @Test
  public void createAndSendMessage() {
    given()
        .body(mapOf("notifyCreated", true, "number", "827001.0002", "name", "Brake disc", "remark",
            "Brake discs", "attributes",
            listOf(mapOf("name", "height", "type", AttributeType.NUMERIC, "value", "60"),
                mapOf("name", "diameter ", "type", AttributeType.NUMERIC, "value", "285"),
                mapOf("name", "Bolt hole number", "type", AttributeType.NUMERIC, "value", "5"))))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/products/save/").prettyPrint();

  }

  @Test
  public void delete() {
    given().body(mapOf("id", "535301785166807040")).contentType(MediaType.APPLICATION_JSON).when()
        .post("ddd/products/delete/").prettyPrint();
  }

}
