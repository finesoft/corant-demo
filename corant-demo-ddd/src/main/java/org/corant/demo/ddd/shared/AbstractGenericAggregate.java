package org.corant.demo.ddd.shared;

import static org.corant.suites.bundle.Preconditions.requireNotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.corant.suites.bundle.GlobalMessageCodes;
import org.corant.suites.ddd.model.AbstractDefaultGenericAggregate;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class AbstractGenericAggregate<P, T extends AbstractGenericAggregate<P, T>>
extends AbstractDefaultGenericAggregate<P, T> {

  private static final long serialVersionUID = -4395445831789674052L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "snowflake")
  @GenericGenerator(name = "snowflake",
  strategy = "org.corant.suites.jpa.hibernate.HibernateSnowflakeIdGenerator")
  private Long id;

  public AbstractGenericAggregate() {}

  @Override
  public Long getId() {
    return this.id;
  }

  protected void setId(Long id) {
    this.id = requireNotNull(id, GlobalMessageCodes.ERR_PARAM);
  }
}

