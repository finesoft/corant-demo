package org.corant.demo.ddd.application.command;

import static org.corant.shared.util.Assertions.shouldNotNull;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.corant.demo.ddd.application.parameter.ConfirmOrder;
import org.corant.demo.ddd.application.parameter.DeleteOrder;
import org.corant.demo.ddd.application.parameter.DeliveryOrder;
import org.corant.demo.ddd.application.parameter.MaintainOrder;
import org.corant.demo.ddd.application.parameter.PayOrder;
import org.corant.demo.ddd.domain.Order;
import org.corant.demo.ddd.shared.AbstractService;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.shared.util.ObjectUtils;

@ApplicationScoped
@Transactional
public class OrderService extends AbstractService {

  public void confirm(ConfirmOrder cmd) {
    shouldNotNull(repo.get(Order.class, cmd.getId()), IllegalArgumentException::new)
        .confirm(cmd.getRemark());
  }

  public Long create(MaintainOrder cmd) {
    Order order = new Order(getParticipator(cmd.getSellerId()), getParticipator(cmd.getBuyerId()),
        cmd.getDeliveryInfo(), cmd.getPaymentInfo(), cmd.getRemark());
    cmd.getItems().forEach(it -> order.addItem(getCommodity(it.getCommodityId(), it.getPrice()),
        it.getQty(), it.getRemark()));
    Parameter preserveParam = Parameter.empty();
    if (cmd.isNotifyCreated()) {
      preserveParam = preserveParam.withAttribute("notifyCreated", true);
    }
    return order.preserve(preserveParam, null).getId();
  }

  public void delete(DeleteOrder cmd) {
    shouldNotNull(repo.get(Order.class, cmd.getId()), IllegalArgumentException::new)
        .destroy(Parameter.empty(), null);
  }

  public void delivery(DeliveryOrder cmd) {
    shouldNotNull(repo.get(Order.class, cmd.getId()), IllegalArgumentException::new)
        .delivery(cmd.getRemark());
  }

  public void pay(PayOrder cmd) {
    shouldNotNull(repo.get(Order.class, cmd.getId()), IllegalArgumentException::new)
        .pay(cmd.getRemark());
  }

  public void update(MaintainOrder cmd) {
    Order order = shouldNotNull(repo.get(Order.class, cmd.getId()), IllegalArgumentException::new)
        .changeDeliveryInfo(cmd.getDeliveryInfo()).changePaymentInfo(cmd.getPaymentInfo())
        .changeRemark(cmd.getRemark());
    order.removeItemIf(ObjectUtils.emptyPredicate(true));
    cmd.getItems().forEach(it -> order.addItem(getCommodity(it.getCommodityId(), it.getPrice()),
        it.getQty(), it.getRemark()));
  }

}
