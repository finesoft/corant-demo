package org.corant.demo.ddd.domain.message;

import org.corant.suites.ddd.message.AbstractAggregateMessage;
import org.corant.suites.ddd.message.AggregateMessageMetadata;
import org.corant.suites.ddd.model.Aggregate;
import org.corant.suites.jms.shared.annotation.MessageSend;

@MessageSend(destination = "corant-demo-products")
public class ProductCreatedMessage extends AbstractAggregateMessage {

  private static final long serialVersionUID = 332423865364372397L;

  public ProductCreatedMessage() {
    super();
  }

  public ProductCreatedMessage(Aggregate aggregate) {
    super(aggregate);
  }

  public ProductCreatedMessage(AggregateMessageMetadata metadata) {
    super(metadata);
  }

}
