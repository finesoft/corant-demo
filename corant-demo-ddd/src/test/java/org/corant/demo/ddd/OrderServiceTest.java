package org.corant.demo.ddd;

import static org.corant.shared.util.CollectionUtils.listOf;
import static org.corant.shared.util.MapUtils.mapOf;
import static org.corant.suites.cdi.Instances.resolve;
import javax.transaction.Transactional;
import org.corant.demo.ddd.application.OrderService;
import org.corant.demo.ddd.application.ProductService;
import org.corant.demo.ddd.application.UserService;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.junit.Test;

public class OrderServiceTest extends AbstractTest {

  @Test
  public void confirm() {
    resolve(OrderService.class).confirm(mapOf("id", "535302305260503040", "remark", "confirmed!"));
  }

  @Test
  public void create() {
    resolve(OrderService.class).create(mapOf("sellerId", "535284030677975040", "buyerId",
        "535285121197015040", "remark", "The second order", "deliveryInfo", "deliveryInfo",
        "paymentInfo", "paymentInfo", "items",
        listOf(mapOf("commodityId", "535294256391127040", "price", "20.5", "qty", "1230"),
            mapOf("commodityId", "535295099446231040", "price", "23.5", "qty", "99"),
            mapOf("commodityId", "535299352470487040", "price", "25", "qty", "123"))));
  }

  @Test
  public void createAndSendMessage() {
    resolve(OrderService.class).create(mapOf("issueMessage", true, "sellerId", "535284030677975040",
        "buyerId", "535285121197015040", "remark", "The second order", "deliveryInfo",
        "deliveryInfo", "paymentInfo", "paymentInfo", "items",
        listOf(mapOf("commodityId", "535294256391127040", "price", "20.5", "qty", "1230"),
            mapOf("commodityId", "535295099446231040", "price", "23.5", "qty", "99"),
            mapOf("commodityId", "535299352470487040", "price", "25", "qty", "123"))));
  }

  @Test
  public void delete() {
    resolve(OrderService.class).delete(mapOf("id", "535301785166807040"));
  }

  @Test
  public void delivery() {
    resolve(OrderService.class).delivery(mapOf("id", "535302305260503040", "remark", "delivered!"));
  }

  @Test
  public void pay() {
    resolve(OrderService.class).pay(mapOf("id", "535302305260503040", "remark", "payed!"));
  }

  @Test
  @Transactional
  public void test() {
    resolve(OrderService.class).create(mapOf("issueMessage", true, "sellerId",
        resolve(UserService.class).create(mapOf("name", "bingo-seller", "issueMessage", true)),
        "buyerId",
        resolve(UserService.class).create(mapOf("name", "bingo-buyer", "issueMessage", true)),
        "remark", "The all order", "deliveryInfo", "deliveryInfo", "paymentInfo", "paymentInfo",
        "items",
        listOf(mapOf("commodityId", resolve(ProductService.class).create(mapOf("issueMessage", true,
            "number", "827001.0002", "name", "Brake disc", "remark", "Brake discs", "attributes",
            listOf(mapOf("name", "height", "type", AttributeType.NUMBERIC, "value", "60"),
                mapOf("name", "diameter ", "type", AttributeType.NUMBERIC, "value", "285"),
                mapOf("name", "Bolt hole number", "type", AttributeType.NUMBERIC, "value", "5")))),
            "price", "20.5", "qty", "1230"))));

  }

  @Test
  public void update() {
    // 535302305260503040
    resolve(OrderService.class).update(mapOf("id", "535302305260503040", "sellerId",
        "535284030677975040", "buyerId", "535285121197015040", "remark", "The second order",
        "deliveryInfo", "deliveryInfo", "paymentInfo", "paymentInfo", "items",
        listOf(mapOf("commodityId", "535294256391127040", "price", "25.5", "qty", "1230"),
            mapOf("commodityId", "535295099446231040", "price", "43.5", "qty", "99"),
            mapOf("commodityId", "535299352470487040", "price", "35", "qty", "123"))));
  }
}
