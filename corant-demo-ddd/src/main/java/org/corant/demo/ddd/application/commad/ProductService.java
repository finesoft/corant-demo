package org.corant.demo.ddd.application.commad;

import static org.corant.shared.util.MapUtils.getMapBoolean;
import static org.corant.shared.util.MapUtils.getMapEnum;
import static org.corant.shared.util.MapUtils.getMapList;
import static org.corant.shared.util.MapUtils.getMapLong;
import static org.corant.shared.util.MapUtils.getMapObject;
import static org.corant.shared.util.MapUtils.getMapString;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.corant.demo.ddd.domain.Product;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.shared.util.ObjectUtils.Triple;

@ApplicationScoped
@Transactional
public class ProductService extends AbstractService {

  public Long create(Map<String, Object> cmd) {
    return new Product(getMapString(cmd, "number"), getMapString(cmd, "name"),
        getMapString(cmd, "remark"),
        getMapList(cmd, "attributes",
            a -> Triple.of(getMapString((Map<?, ?>) a, "name"),
                getMapEnum((Map<?, ?>) a, "type", AttributeType.class),
                getMapObject((Map<?, ?>) a, "value"))))
                    .preserve(
                        getMapBoolean(cmd, "issueMessage")
                            ? Parameter.empty().withAttribute("issueMessage", true)
                            : Parameter.empty(),
                        (p, u) -> System.out
                            .println("Create product " + u.getNumber() + "\t" + u.getId()))
                    .getId();
  }

  public void delete(Map<String, Object> cmd) {
    repo.get(Product.class, getMapLong(cmd, "id")).destroy(Parameter.empty(),
        (p, u) -> System.out.println("Delete product " + u.getName()));
  }

  public void update(Map<String, Object> cmd) {
    Product product = repo.get(Product.class, getMapLong(cmd, "id"));
    if (cmd.containsKey("number")) {
      product.changeNumber(getMapString(cmd, "number"));
    }
    if (cmd.containsKey("name")) {
      product.changeName(getMapString(cmd, "name"));
    }
    if (cmd.containsKey("remark")) {
      product.changeRemark(getMapString(cmd, "remark"));
    }
    if (cmd.containsKey("attributes")) {
      product.changeAttributes(getMapList(cmd, "attributes",
          a -> Triple.of(getMapString((Map<?, ?>) a, "name"),
              getMapEnum((Map<?, ?>) a, "type", AttributeType.class),
              getMapObject((Map<?, ?>) a, "value"))));
    }
  }

}
