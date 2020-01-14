package org.corant.demo.ddd.application;

import static org.corant.shared.util.MapUtils.getMapBigDecimal;
import static org.corant.shared.util.MapUtils.getMapBoolean;
import static org.corant.shared.util.MapUtils.getMapInteger;
import static org.corant.shared.util.MapUtils.getMapLong;
import static org.corant.shared.util.MapUtils.getMapMaps;
import static org.corant.shared.util.MapUtils.getMapString;
import java.math.BigDecimal;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.corant.demo.ddd.domain.Order;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.demo.ddd.ubiquity.Participator;

@ApplicationScoped
@Transactional
public class OrderService extends AbstractService {

  public void confirm(Map<String, Object> cmd) {
    repo.get(Order.class, getMapLong(cmd, "id")).confirm(getMapString(cmd, "remark"));
  }

  public Long create(Map<String, Object> cmd) {
    Participator seller = getParticipator(getMapLong(cmd, "sellerId"));
    Participator buyer = getParticipator(getMapLong(cmd, "buyerId"));
    Order order = new Order(seller, buyer, getMapString(cmd, "deliveryInfo"),
        getMapString(cmd, "paymentInfo"), getMapString(cmd, "remark"));
    getMapMaps(cmd, "items").forEach(it -> {
      order.addItem(
          getCommodity(getMapLong(it, "commodityId"),
              getMapBigDecimal(it, "price", BigDecimal.ONE)),
          getMapInteger(it, "qty"), getMapString(cmd, "remark"));
    });
    return order.preserve(
        getMapBoolean(cmd, "issueMessage") ? Parameter.empty().withAttribute("issueMessage", true)
            : Parameter.empty(),
        (p, u) -> System.out.println("Create order " + u.getNumber() + "\t" + u.getId())).getId();
  }

  public void delete(Map<String, Object> cmd) {
    repo.get(Order.class, getMapLong(cmd, "id")).destroy(Parameter.empty(),
        (p, u) -> System.out.println("Delete order " + u.getNumber()));
  }

  public void delivery(Map<String, Object> cmd) {
    repo.get(Order.class, getMapLong(cmd, "id")).delivery(getMapString(cmd, "remark"));
  }

  public void pay(Map<String, Object> cmd) {
    repo.get(Order.class, getMapLong(cmd, "id")).pay(getMapString(cmd, "remark"));
  }

  public void update(Map<String, Object> cmd) {
    Order order = repo.get(Order.class, getMapLong(cmd, "id"));
    if (cmd.containsKey("deliveryInfo")) {
      order.changeDeliveryInfo(getMapString(cmd, "deliveryInfo"));
    }
    if (cmd.containsKey("paymentInfo")) {
      order.changePaymentInfo(getMapString(cmd, "paymentInfo"));
    }
    if (cmd.containsKey("remark")) {
      order.changeRemark(getMapString(cmd, "remark"));
    }
    if (cmd.containsKey("items")) {
      order.removeItemIf(t -> true);
      getMapMaps(cmd, "items").forEach(it -> {
        order.addItem(
            getCommodity(getMapLong(it, "commodityId"),
                getMapBigDecimal(it, "price", BigDecimal.ONE)),
            getMapInteger(it, "qty"), getMapString(cmd, "remark"));
      });
    }
  }

}
