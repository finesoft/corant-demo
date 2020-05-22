package org.corant.demo.ddd.application.commad;

import static org.corant.shared.util.MapUtils.getMapBoolean;
import static org.corant.shared.util.MapUtils.getMapLong;
import static org.corant.shared.util.MapUtils.getMapString;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.corant.demo.ddd.domain.User;
import org.corant.demo.ddd.ubiquity.Parameter;

@ApplicationScoped
@Transactional
public class UserService extends AbstractService {

  public Long create(Map<String, Object> cmd) {
    return new User(getMapString(cmd, "name")).preserve(
        getMapBoolean(cmd, "issueMessage") ? Parameter.empty().withAttribute("issueMessage", true)
            : Parameter.empty(),
        (p, u) -> System.out.println("Create user " + u.getName() + "\t" + u.getId())).getId();
  }

  public void delete(Map<String, Object> cmd) {
    repo.get(User.class, getMapLong(cmd, "id")).destroy(Parameter.empty(),
        (p, u) -> System.out.println("Delete user " + u.getName()));
  }

  public void update(Map<String, Object> cmd) {
    repo.get(User.class, getMapLong(cmd, "id")).changeName(getMapString(cmd, "name"));
  }

}
