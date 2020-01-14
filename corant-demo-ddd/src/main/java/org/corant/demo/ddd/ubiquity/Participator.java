package org.corant.demo.ddd.ubiquity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import org.corant.suites.ddd.model.Value;

@MappedSuperclass
@Embeddable
public class Participator implements Value{

  private static final long serialVersionUID = 3986120922080818760L;

  @Column
  private Long id;

  @Column
  private String name;

  public Participator(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  protected Participator() {

  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Participator other = (Participator) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (id == null ? 0 : id.hashCode());
    result = prime * result + (name == null ? 0 : name.hashCode());
    return result;
  }


}
