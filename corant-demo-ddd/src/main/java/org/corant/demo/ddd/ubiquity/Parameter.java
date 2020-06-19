package org.corant.demo.ddd.ubiquity;

import static org.corant.shared.util.Maps.mapOf;
import java.util.Map;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.DynamicAttributeMap;
import org.corant.suites.ddd.model.Value;

public class Parameter implements Value {

  static final Parameter EMPTY_INST = new Parameter();

  private static final long serialVersionUID = -3517537674124343136L;

  private final DynamicAttributeMap attributes = new DynamicAttributeMap();

  private Participator operator = null;

  protected Parameter() {}

  protected Parameter(Participator operator, DynamicAttributeMap attributes) {
    if (attributes != null) {
      this.attributes.putAll(attributes);
    }
    this.operator = operator;
  }

  public static Parameter empty() {
    return EMPTY_INST;
  }

  public static Parameter of(DynamicAttributeMap attributes) {
    return new Parameter(null, attributes);
  }

  public static Parameter of(Participator operator) {
    return new Parameter(operator, null);
  }

  public static Parameter of(Participator operator, DynamicAttributeMap attributes) {
    return new Parameter(operator, attributes);
  }

  public Parameter clearAttributes() {
    return new Parameter(operator, null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Parameter other = (Parameter) obj;
    if (attributes == null) {
      if (other.attributes != null) {
        return false;
      }
    } else if (!attributes.equals(other.attributes)) {
      return false;
    }
    if (operator == null) {
      if (other.operator != null) {
        return false;
      }
    } else if (!operator.equals(other.operator)) {
      return false;
    }
    return true;
  }

  public DynamicAttributeMap getAttributes() {
    return attributes.unmodifiable();
  }

  public Participator getOperator() {
    return operator == null ? null : operator;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + attributes.hashCode();
    result = prime * result + (operator == null ? 0 : operator.hashCode());
    return result;
  }

  public Parameter removeAttribute(String name) {
    Parameter newer = new Parameter(operator, attributes);
    newer.attributes.remove(name);
    return newer;
  }

  public Parameter withAttribute(Map<?, ?> map) {
    Parameter newer = new Parameter(operator, attributes);
    map.forEach((k, v) -> {
      if (k != null) {
        newer.attributes.put(k.toString(), v);
      }
    });
    return newer;
  }

  public Parameter withAttribute(String name, Object value) {
    Parameter newer = new Parameter(operator, attributes);
    newer.attributes.put(name, value);
    return newer;
  }

  public Parameter withAttributeIfAbsent(String name, Object value) {
    Parameter newer = new Parameter(operator, attributes);
    newer.attributes.putIfAbsent(name, value);
    return newer;
  }

  public Parameter withAttributes(Object... attrs) {
    Parameter newer = new Parameter(operator, attributes);
    newer.attributes.putAll(mapOf(attrs));
    return newer;
  }

}
