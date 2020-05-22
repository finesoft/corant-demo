package org.corant.demo.ddd.domain;

import static org.corant.shared.util.ObjectUtils.isEquals;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.corant.demo.ddd.domain.message.UserCreatedMessage;
import org.corant.demo.ddd.domain.message.UserNameChangedMessage;
import org.corant.demo.ddd.shared.AbstractGenericAggregate;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.demo.ddd.ubiquity.Participator;

@Entity
@Table(name = "t_user")
public class User extends AbstractGenericAggregate<Parameter, User> {

  private static final long serialVersionUID = 7726976248905779016L;

  @Column
  private String name;

  @Column(updatable = false)
  private Instant createdTime;

  @Column
  private Instant lastModifiedTime;

  public User(String name) {
    this.name = name;
  }

  protected User() {

  }

  public Participator asParticipator() {
    return new Participator(getId(), getName());
  }

  public User changeName(String name) {
    String oldName = getName();
    if (!isEquals(oldName, name)) {
      this.name = name;
      raise(new UserNameChangedMessage(this, oldName, name));
    }
    return this;
  }

  public String getName() {
    return name;
  }

  @Override
  public User preserve(Parameter param, PreservingHandler<Parameter, User> handler) {
    final boolean isNew = isPhantom();
    super.preserve(param, handler);
    if (isNew && param.getAttributes().getBoolean("notifyCreated")) {
      raise(new UserCreatedMessage(this));
    }
    return this;
  }

  @Override
  protected void onPrePreserve() {
    if (getId() == null) {
      createdTime = Instant.now();
    } else {
      lastModifiedTime = Instant.now();
    }
  }

}
