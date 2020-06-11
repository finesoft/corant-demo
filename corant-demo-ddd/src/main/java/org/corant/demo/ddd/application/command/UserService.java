package org.corant.demo.ddd.application.command;

import static org.corant.shared.util.Assertions.shouldNotNull;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.corant.demo.ddd.application.parameter.DeleteUser;
import org.corant.demo.ddd.application.parameter.MaintainUser;
import org.corant.demo.ddd.domain.User;
import org.corant.demo.ddd.shared.AbstractService;
import org.corant.demo.ddd.ubiquity.Parameter;

@ApplicationScoped
@Transactional
public class UserService extends AbstractService {

  public Long create(MaintainUser cmd) {
    return new User(cmd.getName())
        .preserve(cmd.isNotifyCreated() ? Parameter.empty().withAttribute("notifyCreated", true)
            : Parameter.empty(), null)
        .getId();
  }

  public void delete(DeleteUser cmd) {
    shouldNotNull(repo.get(User.class, cmd.getId()), IllegalArgumentException::new)
        .destroy(Parameter.empty(), null);
  }

  public void update(MaintainUser cmd) {
    shouldNotNull(repo.get(User.class, cmd.getId()), IllegalArgumentException::new)
        .changeName(cmd.getName());
  }

}
