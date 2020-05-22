package org.corant.demo.ddd;

import static org.corant.shared.util.CollectionUtils.listOf;
import static org.corant.shared.util.MapUtils.mapOf;
import static org.corant.suites.cdi.Instances.resolve;
import org.corant.demo.ddd.application.commad.ProductService;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.junit.Test;

public class ProductServiceTest extends AbstractTest {

  @Test
  public void create() {
    resolve(ProductService.class).create(
        mapOf("number", "827001.0002", "name", "Brake disc", "remark", "Brake discs", "attributes",
            listOf(mapOf("name", "height", "type", AttributeType.NUMERIC, "value", "60"),
                mapOf("name", "diameter ", "type", AttributeType.NUMERIC, "value", "285"),
                mapOf("name", "Bolt hole number", "type", AttributeType.NUMERIC, "value", "5"))));
  }

  @Test
  public void createAndSendMessage() {
    resolve(ProductService.class).create(mapOf("issueMessage", true, "number", "827001.0002",
        "name", "Brake disc", "remark", "Brake discs", "attributes",
        listOf(mapOf("name", "height", "type", AttributeType.NUMERIC, "value", "60"),
            mapOf("name", "diameter ", "type", AttributeType.NUMERIC, "value", "285"),
            mapOf("name", "Bolt hole number", "type", AttributeType.NUMERIC, "value", "5"))));

  }

  @Test
  public void delete() {
    resolve(ProductService.class).delete(mapOf("id", "535293069403095040"));
  }

  @Test
  public void update() {
    resolve(ProductService.class).update(mapOf("id", "535299352470487040", "number", "827001.0004",
        "name", "Brake discx", "remark", "Brake discs", "attributes",
        listOf(mapOf("name", "height", "type", AttributeType.NUMERIC, "value", "65"),
            mapOf("name", "diameter ", "type", AttributeType.NUMERIC, "value", "288"),
            mapOf("name", "Bolt hole number", "type", AttributeType.NUMERIC, "value", "6"))));
  }

}
