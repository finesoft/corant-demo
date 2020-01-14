package org.corant.demo.ddd.infrastructure;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.corant.suites.ddd.message.Message;
import org.corant.suites.ddd.message.MessageDispatcher;
import org.corant.suites.jms.shared.send.AnnotatedMessageSender;

@ApplicationScoped
@Transactional
public class DefaultJMSMessageDispatcher extends AnnotatedMessageSender
    implements MessageDispatcher {

  @Override
  public void accept(Message[] t) {
    super.send((Serializable[]) t);
  }

}
