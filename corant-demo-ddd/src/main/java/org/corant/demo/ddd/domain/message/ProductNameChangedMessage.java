package org.corant.demo.ddd.domain.message;

import org.corant.demo.ddd.domain.Product;
import org.corant.shared.util.ObjectUtils;
import org.corant.suites.ddd.message.AbstractAggregateMessage;
import org.corant.suites.ddd.message.MergableMessage;
import org.corant.suites.jms.shared.annotation.MessageSend;

@MessageSend(destination = "corant-demo-products")
public class ProductNameChangedMessage extends AbstractAggregateMessage implements MergableMessage {

  private static final long serialVersionUID = -4803695097807990422L;

  private String oldName;
  private String newName;

  public ProductNameChangedMessage(Product aggregate, String oldName, String newName) {
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
    return !ObjectUtils.isEquals(oldName, newName);
  }

  @Override
  public MergableMessage merge(MergableMessage other) {
    ProductNameChangedMessage o = (ProductNameChangedMessage) other;
    oldName = o.oldName;
    return this;
  }

}
