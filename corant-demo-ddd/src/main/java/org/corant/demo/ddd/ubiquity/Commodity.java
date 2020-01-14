package org.corant.demo.ddd.ubiquity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import org.corant.suites.ddd.model.Value;

@MappedSuperclass
@Embeddable
public class Commodity implements Value {

  private static final long serialVersionUID = -5761933919404101125L;

  @Column
  private Long id;

  @Column
  private String name;

  @Column
  private String number;

  @Column
  private BigDecimal price;

  public Commodity(Long id, String name, String number, BigDecimal price) {
    super();
    this.id = id;
    this.name = name;
    this.number = number;
    this.price = price;
  }

  protected Commodity() {

  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public BigDecimal getPrice() {
    return price;
  }

}
