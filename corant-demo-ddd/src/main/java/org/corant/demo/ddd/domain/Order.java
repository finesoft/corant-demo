package org.corant.demo.ddd.domain;

import static org.corant.shared.util.ConversionUtils.toBigDecimal;
import static org.corant.suites.bundle.Preconditions.requireTrue;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.corant.demo.ddd.domain.message.OrderCreatedMessage;
import org.corant.demo.ddd.domain.message.OrderStatusChangedMessage;
import org.corant.demo.ddd.shared.AbstractGenericAggregate;
import org.corant.demo.ddd.ubiquity.Commodity;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.demo.ddd.ubiquity.Participator;
import org.corant.suites.bundle.GlobalMessageCodes;

@Entity
@Table(name = "t_order")
public class Order extends AbstractGenericAggregate<Parameter, Order> {

  private static final long serialVersionUID = 4249560115267447964L;

  @Embedded
  @AttributeOverride(column = @Column(name = "sellerId", updatable = false), name = "id")
  @AttributeOverride(column = @Column(name = "sellerName", length = 320), name = "name")
  private Participator seller;

  @Embedded
  @AttributeOverride(column = @Column(name = "buyerId", updatable = false), name = "id")
  @AttributeOverride(column = @Column(name = "buyerName", length = 320), name = "name")
  private Participator buyer;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "orderId", foreignKey = @ForeignKey(name = "fk_order_item"))
  private List<OrderItem> items = new ArrayList<>();

  @Column(updatable = false)
  private String number;

  @Lob
  @Basic
  private String deliveryInfo;

  @Lob
  @Basic
  private String paymentInfo;

  @Lob
  @Basic
  private String remark;

  @Lob
  @Basic
  private String confirmedRemark;

  @Lob
  @Basic
  private String payedRemark;

  @Lob
  @Basic
  private String deliveriedRemark;

  @Column(updatable = false)
  private Instant createdTime;

  @Column
  private Instant lastModifiedTime;

  @Column
  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.UNCONFIRMED;

  @Column
  private BigDecimal amount;

  public Order() {

  }

  public Order(Participator seller, Participator buyer, String deliveryInfo, String paymentInfo,
      String remark) {
    super();
    this.seller = seller;
    this.buyer = buyer;
    this.deliveryInfo = deliveryInfo;
    this.paymentInfo = paymentInfo;
    this.remark = remark;
  }

  public boolean addItem(Commodity commodity, int qty, String remark) {
    requireTrue(status == OrderStatus.UNCONFIRMED, GlobalMessageCodes.ERR_PARAM);
    Optional<OrderItem> op =
        items.stream().filter(p -> p.getCommodity().getId().equals(commodity.getId())).findFirst();
    if (op.isPresent()) {
      op.get().setCommodity(commodity).setQty(qty).setRemark(remark);
      calAmount();
      return false;
    } else {
      items.add(new OrderItem().setCommodity(commodity).setQty(qty).setRemark(remark));
      calAmount();
      return true;
    }
  }

  public Order changeDeliveryInfo(String deliveryInfo) {
    requireTrue(status == OrderStatus.UNCONFIRMED, GlobalMessageCodes.ERR_PARAM);
    this.deliveryInfo = deliveryInfo;
    return this;
  }

  public Order changePaymentInfo(String paymentInfo) {
    requireTrue(status == OrderStatus.UNCONFIRMED, GlobalMessageCodes.ERR_PARAM);
    this.paymentInfo = paymentInfo;
    return this;
  }

  public Order changeRemark(String remark) {
    requireTrue(status == OrderStatus.UNCONFIRMED, GlobalMessageCodes.ERR_PARAM);
    this.remark = remark;
    return this;
  }

  public Order confirm(String remark) {
    requireTrue(status == OrderStatus.UNCONFIRMED, GlobalMessageCodes.ERR_PARAM);
    status = OrderStatus.CONFIRMED;
    confirmedRemark = remark;
    raise(new OrderStatusChangedMessage(this));
    return this;
  }

  public Order delivery(String remark) {
    requireTrue(status == OrderStatus.PAYED, GlobalMessageCodes.ERR_PARAM);
    status = OrderStatus.DELIVERIED;
    deliveriedRemark = remark;
    raise(new OrderStatusChangedMessage(this));
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Participator getBuyer() {
    return buyer;
  }

  public String getConfirmedRemark() {
    return confirmedRemark;
  }

  public Instant getCreatedTime() {
    return createdTime;
  }

  public String getDeliveriedRemark() {
    return deliveriedRemark;
  }

  public String getDeliveryInfo() {
    return deliveryInfo;
  }

  public List<OrderItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public Instant getLastModifiedTime() {
    return lastModifiedTime;
  }

  public String getNumber() {
    return number;
  }

  public String getPayedRemark() {
    return payedRemark;
  }

  public String getPaymentInfo() {
    return paymentInfo;
  }

  public String getRemark() {
    return remark;
  }

  public Participator getSeller() {
    return seller;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public Order pay(String remark) {
    requireTrue(status == OrderStatus.CONFIRMED, GlobalMessageCodes.ERR_PARAM);
    status = OrderStatus.PAYED;
    payedRemark = remark;
    raise(new OrderStatusChangedMessage(this));
    return this;
  }

  @Override
  public Order preserve(Parameter param, PreservingHandler<Parameter, Order> handler) {
    final boolean isNew = isPhantom();
    super.preserve(param, handler);
    if (isNew && param.getAttributes().getBoolean("notifyCreated")) {
      raise(new OrderCreatedMessage(this));
    }
    return this;
  }

  public boolean removeItemIf(Predicate<OrderItem> predicate) {
    requireTrue(status == OrderStatus.UNCONFIRMED, GlobalMessageCodes.ERR_PARAM);
    boolean removed = items.removeIf(predicate);
    if (removed) {
      calAmount();
    }
    return removed;
  }

  @Override
  protected void onPrePreserve() {
    if (getId() == null) {
      createdTime = Instant.now();
      number = "ORD-" + Instant.now().toEpochMilli();
    } else {
      lastModifiedTime = Instant.now();
    }
  }

  private void calAmount() {
    amount = items.stream().map(i -> i.getCommodity().getPrice().multiply(toBigDecimal(i.getQty())))
        .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add)
        .setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  public enum OrderStatus {
    UNCONFIRMED, CONFIRMED, PAYED, DELIVERIED
  }
}
