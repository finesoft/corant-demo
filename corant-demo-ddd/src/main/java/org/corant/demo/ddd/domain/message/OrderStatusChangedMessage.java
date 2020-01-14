package org.corant.demo.ddd.domain.message;

import org.corant.demo.ddd.domain.Order;
import org.corant.demo.ddd.domain.Order.OrderStatus;
import org.corant.suites.ddd.message.AbstractAggregateMessage;
import org.corant.suites.ddd.message.MergableMessage;
import org.corant.suites.jms.shared.annotation.MessageSend;

@MessageSend(destination = "corant-demo-orders")
public class OrderStatusChangedMessage extends AbstractAggregateMessage implements MergableMessage {

  private static final long serialVersionUID = 332423865364372397L;

  private OrderStatus status;

  public OrderStatusChangedMessage() {
    super();
  }

  public OrderStatusChangedMessage(Order aggregate) {
    super(aggregate);
    status = aggregate.getStatus();
  }

  public OrderStatus getStatus() {
    return status;
  }

}
