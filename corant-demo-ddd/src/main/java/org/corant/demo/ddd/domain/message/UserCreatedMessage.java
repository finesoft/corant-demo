package org.corant.demo.ddd.domain.message;

import org.corant.suites.ddd.message.AbstractAggregateMessage;
import org.corant.suites.ddd.message.AggregateMessageMetadata;
import org.corant.suites.ddd.model.Aggregate;
import org.corant.suites.jms.shared.annotation.MessageSend;

@MessageSend(destination = "corant-demo-users")
public class UserCreatedMessage extends AbstractAggregateMessage {

  private static final long serialVersionUID = 332423865364372397L;

  public UserCreatedMessage() {
    super();
  }

  public UserCreatedMessage(Aggregate aggregate) {
    super(aggregate);
  }

  public UserCreatedMessage(AggregateMessageMetadata metadata) {
    super(metadata);
  }

}
