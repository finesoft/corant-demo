package org.corant.demo.ddd.domain.message;

import org.corant.suites.ddd.message.AbstractAggregateMessage;
import org.corant.suites.ddd.message.AggregateMessageMetadata;
import org.corant.suites.ddd.model.Aggregate;
import org.corant.suites.jms.shared.annotation.MessageSend;

@MessageSend(destination = "corant-demo-orders")
public class OrderCreatedMessage extends AbstractAggregateMessage {

  private static final long serialVersionUID = 332423865364372397L;

  public OrderCreatedMessage() {
    super();
  }

  public OrderCreatedMessage(Aggregate aggregate) {
    super(aggregate);
  }

  public OrderCreatedMessage(AggregateMessageMetadata metadata) {
    super(metadata);
  }

}
