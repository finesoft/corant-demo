package org.corant.demo.ddd;

import static org.corant.shared.util.CollectionUtils.listOf;
import static org.corant.shared.util.MapUtils.mapOf;
import static org.corant.suites.cdi.Instances.resolve;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.corant.demo.ddd.application.commad.OrderService;
import org.corant.demo.ddd.application.commad.ProductService;
import org.corant.demo.ddd.application.commad.UserService;
import org.corant.demo.ddd.application.query.OrderQueryService;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.corant.suites.json.JsonUtils;
import org.corant.suites.query.shared.QueryService.Paging;
import org.junit.Test;

public class OrderServiceTest extends AbstractTest {

  @Inject
  OrderService orderService;
  @Inject
  OrderQueryService orderQueryService;

  @Test
  public void confirm() {
    orderService.confirm(mapOf("id", "535302305260503040", "remark", "confirmed!"));
  }

  @Test
  public void create() {
    orderService.create(mapOf("sellerId", "535284030677975040", "buyerId", "535285121197015040",
        "remark", "The second order", "deliveryInfo", "deliveryInfo", "paymentInfo", "paymentInfo",
        "items",
        listOf(mapOf("commodityId", "535294256391127040", "price", "20.5", "qty", "1230"),
            mapOf("commodityId", "535295099446231040", "price", "23.5", "qty", "99"),
            mapOf("commodityId", "535299352470487040", "price", "25", "qty", "123"))));
  }

  @Test
  public void createAndSendMessage() {
    orderService.create(mapOf("issueMessage", true, "sellerId", "535284030677975040", "buyerId",
        "535285121197015040", "remark", "The second order", "deliveryInfo", "deliveryInfo",
        "paymentInfo", "paymentInfo", "items",
        listOf(mapOf("commodityId", "535294256391127040", "price", "20.5", "qty", "1230"),
            mapOf("commodityId", "535295099446231040", "price", "23.5", "qty", "99"),
            mapOf("commodityId", "535299352470487040", "price", "25", "qty", "123"))));
  }

  @Test
  public void delete() {
    orderService.delete(mapOf("id", "535301785166807040"));
  }

  @Test
  public void delivery() {
    orderService.delivery(mapOf("id", "535302305260503040", "remark", "delivered!"));
  }

  @Test
  public void get() {
    Map<String, Object> list = orderQueryService.get(mapOf("id", 535302305260503040L));
    System.out.println(JsonUtils.toString(list, true));
  }

  @Test
  public void pageSelect() {
    Paging<Map<String, Object>> list = orderQueryService.pageSelect(null);
    System.out.println(JsonUtils.toString(list, true));
  }

  @Test
  public void pay() {
    orderService.pay(mapOf("id", "535302305260503040", "remark", "payed!"));
  }

  @Test
  public void select() {
    List<Map<String, Object>> list = orderQueryService.select(null);
    System.out.println(JsonUtils.toString(list, true));
  }

  @Test
  public void selectDeclarative() {
    OrderQueryService s1 = resolve(OrderQueryService.class);
    OrderQueryService s2 = resolve(OrderQueryService.class);
    Map<OrderQueryService, OrderQueryService> map = new HashMap<>();
    map.put(s1, s2);
    map.put(s2, s1);
    System.out.println(s1.equals(s2));
    System.out.println(s1.hashCode() + "\n" + s2.hashCode());
    System.out.println(s1.toString() + "\n" + s2.toString());
    List<Map<String, Object>> list = map.get(s1).select(null);
    System.out.println(JsonUtils.toString(list, true));
    List<OrderQueryService> sl = new ArrayList<>();
    sl.add(s1);
    sl.add(s2);
    for (OrderQueryService s : sl) {
      System.out.println(JsonUtils.toString(s.select(null), true));
    }
    Set<OrderQueryService> ss = new HashSet<>(sl);
    System.out.println(ss.contains(s2));
    System.out.println(ss.contains(s1));
    for (OrderQueryService s : ss) {
      System.out.println(JsonUtils.toString(s.select(null), true));
    }
    System.out.println(JsonUtils.toString(s1.select(null), true));
  }

  @Test
  @Transactional
  public void test() {
    orderService.create(mapOf("issueMessage", true, "sellerId",
        resolve(UserService.class).create(mapOf("name", "bingo-seller", "issueMessage", true)),
        "buyerId",
        resolve(UserService.class).create(mapOf("name", "bingo-buyer", "issueMessage", true)),
        "remark", "The all order", "deliveryInfo", "deliveryInfo", "paymentInfo", "paymentInfo",
        "items",
        listOf(mapOf("commodityId", resolve(ProductService.class).create(mapOf("issueMessage", true,
            "number", "827001.0002", "name", "Brake disc", "remark", "Brake discs", "attributes",
            listOf(mapOf("name", "height", "type", AttributeType.NUMERIC, "value", "60"),
                mapOf("name", "diameter ", "type", AttributeType.NUMERIC, "value", "285"),
                mapOf("name", "Bolt hole number", "type", AttributeType.NUMERIC, "value", "5")))),
            "price", "20.5", "qty", "1230"))));

  }

  @Test
  public void update() {
    // 535302305260503040
    orderService.update(mapOf("id", "535302305260503040", "sellerId", "535284030677975040",
        "buyerId", "535285121197015040", "remark", "The second order", "deliveryInfo",
        "deliveryInfo", "paymentInfo", "paymentInfo", "items",
        listOf(mapOf("commodityId", "535294256391127040", "price", "25.5", "qty", "1230"),
            mapOf("commodityId", "535295099446231040", "price", "43.5", "qty", "99"),
            mapOf("commodityId", "535299352470487040", "price", "35", "qty", "123"))));
  }
}
