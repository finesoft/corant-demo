package org.corant.demo.ddd;

import static io.restassured.RestAssured.given;
import static org.corant.shared.util.CollectionUtils.listOf;
import static org.corant.shared.util.MapUtils.mapOf;
import javax.ws.rs.core.MediaType;
import org.junit.Test;

public class OrderServiceTest extends AbstractTest {

  @Test
  public void confirm() {
    given().body(mapOf("id", "535302305260503040", "remark", "confirmed!"))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/confirm/").prettyPrint();
  }

  @Test
  public void create() {
    given()
        .body(mapOf("sellerId", "535284030677975040", "buyerId", "535285121197015040", "remark",
            "The second order", "deliveryInfo", "deliveryInfo", "paymentInfo", "paymentInfo",
            "items",
            listOf(mapOf("commodityId", "535294256391127040", "price", "20.5", "qty", "1230"),
                mapOf("commodityId", "535295099446231040", "price", "23.5", "qty", "99"),
                mapOf("commodityId", "535299352470487040", "price", "25", "qty", "123"))))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/save/").prettyPrint();
  }

  @Test
  public void createAndSendMessage() {
    given()
        .body(mapOf("nofityCreated", true, "sellerId", "535284030677975040", "buyerId",
            "535285121197015040", "remark", "The second order", "deliveryInfo", "deliveryInfo",
            "paymentInfo", "paymentInfo", "items",
            listOf(mapOf("commodityId", "535294256391127040", "price", "20.5", "qty", "1230"),
                mapOf("commodityId", "535295099446231040", "price", "23.5", "qty", "99"),
                mapOf("commodityId", "535299352470487040", "price", "25", "qty", "123"))))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/save/").prettyPrint();
  }

  @Test
  public void delete() {
    given().body(mapOf("id", "535301785166807040")).contentType(MediaType.APPLICATION_JSON).when()
        .post("ddd/orders/delete/").prettyPrint();
  }

  @Test
  public void delivery() {
    given().body(mapOf("id", "535302305260503040", "remark", "delivered!"))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/delivery/").prettyPrint();
  }

  @Test
  public void get() {
    given().when().get("ddd/orders/get/535302305260503040").prettyPrint();
  }

  @Test
  public void pageSelect() {
    given().when().post("ddd/orders/query/").prettyPrint();
  }

  @Test
  public void pay() {
    given().body(mapOf("id", "535302305260503040", "remark", "payed!"))
        .contentType(MediaType.APPLICATION_JSON).when().post("ddd/orders/pay/").prettyPrint();
  }

}
