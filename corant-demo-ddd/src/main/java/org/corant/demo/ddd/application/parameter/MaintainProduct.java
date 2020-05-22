package org.corant.demo.ddd.application.parameter;

import java.util.List;
import lombok.Data;

@Data
public class MaintainProduct {
  Long id;
  String number;
  String name;
  String remark;
  List<MaintainProductAttribute> attributes;
  boolean notifyCreated;
}
