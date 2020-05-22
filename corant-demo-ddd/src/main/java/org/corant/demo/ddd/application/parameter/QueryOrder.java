package org.corant.demo.ddd.application.parameter;

import org.corant.demo.ddd.domain.Order.OrderStatus;
import lombok.Data;

@Data
public class QueryOrder {
  Long id;
  Long sellerId;
  Long buyerId;
  OrderStatus status;
}
