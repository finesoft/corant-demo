package org.corant.demo.ddd.application.parameter;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintainOrder {
  Long id;
  Long sellerId;
  Long buyerId;
  String deliveryInfo;
  String paymentInfo;
  List<MaintainOrderItem> items;
  boolean notifyCreated;
  String remark;
}
