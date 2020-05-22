package org.corant.demo.ddd.application.parameter;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MaintainOrderItem {
  Long commodityId;
  BigDecimal price;
  Integer qty;
  String remark;
}
