package org.corant.demo.ddd.domain.message;

import static org.corant.shared.util.Objects.areEqual;
import org.corant.demo.ddd.domain.User;
import org.corant.suites.ddd.message.AbstractAggregateMessage;
import org.corant.suites.ddd.message.MergableMessage;
import org.corant.suites.jms.shared.annotation.MessageSend;

@MessageSend(destination = "corant-demo-users")
public class UserNameChangedMessage extends AbstractAggregateMessage implements MergableMessage {

  private static final long serialVersionUID = -4803695097807990422L;

  private String oldName;
  private String newName;

  public UserNameChangedMessage(User aggregate, String oldName, String newName) {
    super(aggregate);
    this.oldName = oldName;
    this.newName = newName;
  }

  public String getNewName() {
    return newName;
  }

  public String getOldName() {
    return oldName;
  }

  @Override
  public boolean isValid() {
    return !areEqual(oldName, newName);
  }

  @Override
  public MergableMessage merge(MergableMessage other) {
    UserNameChangedMessage o = (UserNameChangedMessage) other;
    oldName = o.oldName;
    return this;
  }

}
