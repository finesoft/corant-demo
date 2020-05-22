package org.corant.demo.ddd;

import static io.restassured.RestAssured.given;
import static org.corant.shared.util.CollectionUtils.listOf;
import static org.corant.shared.util.MapUtils.mapOf;
import javax.ws.rs.core.MediaType;
import org.junit.Test;

public class OrderServiceTest extends AbstractTest {

  @Test
  public void confirm() {
    given().body(mapOf("id", "582009210577879040", "remark", "confirmed!"))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/confirm/").prettyPrint();
  }

  @Test
  public void create() {
    given()
        .body(mapOf("sellerId", "582007398638551040", "buyerId", "582007662879703040", "remark",
            "The second order", "deliveryInfo", "deliveryInfo", "paymentInfo", "paymentInfo",
            "items",
            listOf(mapOf("commodityId", "582008417854423040", "price", "20.5", "qty", "1230"),
                mapOf("commodityId", "582008619181015040", "price", "23.5", "qty", "99"))))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/save/").prettyPrint();
  }

  @Test
  public void createAndSendMessage() {
    given()
        .body(mapOf("notifyCreated", true, "sellerId", "582007398638551040", "buyerId",
            "582007662879703040", "remark", "The second order", "deliveryInfo", "deliveryInfo",
            "paymentInfo", "paymentInfo", "items",
            listOf(mapOf("commodityId", "582008417854423040", "price", "20.5", "qty", "1230"),
                mapOf("commodityId", "582008619181015040", "price", "23.5", "qty", "99"))))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/save/").prettyPrint();
  }

  @Test
  public void delete() {
    given().body(mapOf("id", "582009458041815040")).contentType(MediaType.APPLICATION_JSON).when()
        .post("ddd/orders/delete/").prettyPrint();
  }

  @Test
  public void delivery() {
    given().body(mapOf("id", "582009210577879040", "remark", "delivered!"))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/delivery/").prettyPrint();
  }

  @Test
  public void get() {
    given().when().get("ddd/orders/get/582009210577879040").prettyPrint();
  }

  @Test
  public void pageSelect() {
    given().body(mapOf("criteria", mapOf("sellerId", "582007398638551040")))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/query/").prettyPrint();
  }

  @Test
  public void pay() {
    given().body(mapOf("id", "582009210577879040", "remark", "payed!"))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/pay/").prettyPrint();
  }

}
