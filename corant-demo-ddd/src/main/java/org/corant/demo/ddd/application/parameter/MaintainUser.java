package org.corant.demo.ddd.application.parameter;

import lombok.Data;

@Data
public class MaintainUser {
  Long id;
  String name;
  boolean notifyCreated;
}
