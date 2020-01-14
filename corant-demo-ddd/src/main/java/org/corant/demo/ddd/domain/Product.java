package org.corant.demo.ddd.domain;

import static org.corant.shared.util.Empties.isNotEmpty;
import static org.corant.shared.util.ObjectUtils.isEquals;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.corant.demo.ddd.domain.message.ProductCreatedMessage;
import org.corant.demo.ddd.domain.message.ProductNameChangedMessage;
import org.corant.demo.ddd.shared.AbstractGenericAggregate;
import org.corant.demo.ddd.ubiquity.Commodity;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.shared.util.ObjectUtils;
import org.corant.shared.util.ObjectUtils.Triple;

@Entity
@Table(name = "t_product")
public class Product extends AbstractGenericAggregate<Parameter, Product> {

  private static final long serialVersionUID = 5059061115696381863L;

  @Column
  private String name;

  @Column
  private String number;

  @Column
  private String remark;

  @ElementCollection
  @CollectionTable(name = "t_product_attributes", joinColumns = {
      @JoinColumn(name = "productId", foreignKey = @ForeignKey(name = "fk_product_attributes"))})
  @OrderColumn(name = "idx")
  private List<ProductAttribute> attributes = new ArrayList<>();

  @Column(updatable = false)
  private Instant createdTime;

  @Column
  private Instant lastModifiedTime;

  public Product(String number, String name, String remark,
      List<Triple<String, AttributeType, Object>> attributes) {
    this.name = name;
    this.number = number;
    this.remark = remark;
    if (isNotEmpty(attributes)) {
      attributes.stream().filter(ObjectUtils::isNotNull).forEach(this::addAttribute);
    }
  }

  protected Product() {}

  public boolean addAttribute(Triple<String, AttributeType, Object> cmd) {
    return attributes.add(new ProductAttribute(cmd));
  }

  public Commodity asCommodity(BigDecimal price) {
    return new Commodity(getId(), getName(), getNumber(), price);
  }

  public Product changeAttributes(List<Triple<String, AttributeType, Object>> attributes) {
    removeAttribute(a -> true);
    if (isNotEmpty(attributes)) {
      attributes.stream().filter(ObjectUtils::isNotNull).forEach(this::addAttribute);
    }
    return this;
  }

  public Product changeName(String name) {
    String oldName = getName();
    if (!isEquals(oldName, name)) {
      this.name = name;
      raise(new ProductNameChangedMessage(this, oldName, name));
    }
    return this;
  }

  public Product changeNumber(String number) {
    this.number = number;
    return this;
  }

  public Product changeRemark(String remark) {
    this.remark = remark;
    return this;
  }

  public List<ProductAttribute> getAttributes() {
    return Collections.unmodifiableList(attributes);
  }

  public Instant getCreatedTime() {
    return createdTime;
  }

  public Instant getLastModifiedTime() {
    return lastModifiedTime;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public String getRemark() {
    return remark;
  }

  @Override
  public Product preserve(Parameter param, PreservingHandler<Parameter, Product> handler) {
    super.preserve(param, handler);
    if (param.getAttributes().getBoolean("issueMessage")) {
      raise(new ProductCreatedMessage(this));
    }
    return this;
  }

  public boolean removeAttribute(Predicate<ProductAttribute> p) {
    return attributes.removeIf(p);
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
