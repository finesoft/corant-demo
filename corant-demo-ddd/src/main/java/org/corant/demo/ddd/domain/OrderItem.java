package org.corant.demo.ddd.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.corant.demo.ddd.shared.AbstractDefaultEntity;
import org.corant.demo.ddd.ubiquity.Commodity;

@Entity
@Table(name = "t_order_items")
public class OrderItem extends AbstractDefaultEntity {

  private static final long serialVersionUID = -6121179171603813176L;

  @Embedded
  @AttributeOverride(column = @Column(name = "commodityId", updatable = false), name = "id")
  @AttributeOverride(column = @Column(name = "commodityName", length = 320), name = "name")
  @AttributeOverride(column = @Column(name = "commodityNumber", length = 320), name = "number")
  @AttributeOverride(column = @Column(name = "commodityPrice", length = 320), name = "price")
  private Commodity commodity;

  @Column
  private Integer qty;

  @Column
  private String remark;

  protected OrderItem() {

  }

  public Commodity getCommodity() {
    return commodity;
  }

  public Integer getQty() {
    return qty;
  }

  public String getRemark() {
    return remark;
  }

  OrderItem setCommodity(Commodity commodity) {
    this.commodity = commodity;
    return this;
  }

  OrderItem setQty(Integer qty) {
    this.qty = qty;
    return this;
  }

  OrderItem setRemark(String remark) {
    this.remark = remark;
    return this;
  }

}
