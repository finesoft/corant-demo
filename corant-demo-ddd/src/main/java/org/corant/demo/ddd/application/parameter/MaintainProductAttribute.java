package org.corant.demo.ddd.application.parameter;

import org.corant.demo.ddd.ubiquity.DynamicAttributes;
import lombok.Data;

@Data
public class MaintainProductAttribute {
  String name;
  DynamicAttributes.AttributeType type;
  Object value;
}
