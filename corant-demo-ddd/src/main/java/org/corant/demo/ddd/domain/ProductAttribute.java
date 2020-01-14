package org.corant.demo.ddd.domain;

import static org.corant.shared.util.ConversionUtils.toBigDecimal;
import static org.corant.shared.util.ConversionUtils.toBoolean;
import static org.corant.shared.util.ConversionUtils.toInstant;
import static org.corant.shared.util.ObjectUtils.defaultObject;
import static org.corant.shared.util.ObjectUtils.forceCast;
import static org.corant.shared.util.StringUtils.asDefaultString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.corant.demo.ddd.ubiquity.DynamicAttributes.AttributeType;
import org.corant.shared.util.ObjectUtils.Triple;

@Embeddable
public class ProductAttribute implements Serializable {

  private static final long serialVersionUID = -3219178606502546926L;

  @Column
  private String name;

  @Column
  @Enumerated(EnumType.STRING)
  private AttributeType type;

  @Column
  private String stringValue;

  @Column
  private BigDecimal numericValue;

  @Column
  private Boolean booleanValue = false;

  @Column
  private Instant datetimeValue;

  public ProductAttribute(String name, AttributeType type, Object value) {
    this.name = name;
    this.type = defaultObject(type, AttributeType.STRING);
    if (this.type == AttributeType.NUMBERIC) {
      numericValue = toBigDecimal(value);
    } else if (this.type == AttributeType.BOOLEAN) {
      booleanValue = toBoolean(value);
    } else if (this.type == AttributeType.TEMPORAL) {
      datetimeValue = toInstant(value);
    } else {
      stringValue = asDefaultString(value);
    }
  }

  public ProductAttribute(Triple<String, AttributeType, Object> cmd) {
    this(cmd.getLeft(), cmd.getMiddle(), cmd.getRight());
  }

  protected ProductAttribute() {

  }

  public Boolean getBooleanValue() {
    return booleanValue;
  }

  public Instant getDatetimeValue() {
    return datetimeValue;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getNumericValue() {
    return numericValue;
  }

  public String getStringValue() {
    return stringValue;
  }

  public AttributeType getType() {
    return type;
  }

  public <T> T getValue() {
    if (type == AttributeType.NUMBERIC) {
      return forceCast(numericValue);
    } else if (type == AttributeType.BOOLEAN) {
      return forceCast(booleanValue);
    } else if (type == AttributeType.TEMPORAL) {
      return forceCast(datetimeValue);
    } else {
      return forceCast(stringValue);
    }
  }

}
