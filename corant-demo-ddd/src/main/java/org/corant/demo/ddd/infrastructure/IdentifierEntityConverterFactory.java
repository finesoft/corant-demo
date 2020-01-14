package org.corant.demo.ddd.infrastructure;

import static org.corant.shared.util.Assertions.shouldNotNull;
import static org.corant.shared.util.CollectionUtils.immutableSetOf;
import static org.corant.shared.util.ConversionUtils.toLong;
import static org.corant.shared.util.ObjectUtils.asString;
import static org.corant.shared.util.ObjectUtils.defaultObject;
import static org.corant.suites.cdi.Instances.resolve;
import static org.corant.suites.cdi.Instances.select;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.transaction.Transactional;
import org.corant.kernel.event.PostContainerStartedEvent;
import org.corant.shared.conversion.ConversionException;
import org.corant.shared.conversion.Converter;
import org.corant.shared.conversion.ConverterFactory;
import org.corant.shared.conversion.ConverterRegistry;
import org.corant.shared.conversion.ConverterType;
import org.corant.suites.ddd.annotation.stereotype.InfrastructureServices;
import org.corant.suites.ddd.model.AbstractAggregate.DefaultAggregateIdentifier;
import org.corant.suites.ddd.model.Entity;
import org.corant.suites.ddd.repository.JPARepository;
import org.corant.suites.ddd.repository.JPARepositoryExtension;
import org.corant.suites.jpa.shared.JPAUtils;

@ApplicationScoped
@InfrastructureServices
public class IdentifierEntityConverterFactory implements ConverterFactory<Object, Entity> {

  static final Map<Class<?>, Boolean> cached = new ConcurrentHashMap<>();
  static final Map<Class<?>, Annotation[]> puqCached = new ConcurrentHashMap<>();
  final Set<Class<?>> supportedSourceClass =
      immutableSetOf(Long.class, Long.TYPE, String.class, Entity.class);

  final Logger logger = Logger.getLogger(this.getClass().getName());

  @Transactional
  public <T extends Entity> T convert(Object value, Class<T> targetClass, Map<String, ?> hints) {
    if (value == null) {
      return null;
    }
    Long id = null;
    if (value instanceof Long || value.getClass().equals(Long.TYPE)) {
      id = Long.class.cast(value);
    } else if (value instanceof String) {
      id =  Long.valueOf((String)value);
    } else if (value instanceof DefaultAggregateIdentifier) {
      id = toLong(DefaultAggregateIdentifier.class.cast(value).getId());
    }
    T entity = null;
    if (id != null) {
      Instance<JPARepository> repos = select(JPARepository.class, resolveQualifier(targetClass));
      if (repos.isResolvable()) {
        entity = repos.get().get(targetClass, id);
      }
    }
    return shouldNotNull(entity, "Can not convert %s to %s!", value.toString(),
        targetClass.getSimpleName());
  }

  @Override
  public Converter<Object, Entity> create(Class<Entity> targetClass, Entity defaultValue,
      boolean throwException) {
    return (t, h) -> {
      Entity result = null;
      try {
        result = resolve(IdentifierEntityConverterFactory.class).convert(t, targetClass, h);
      } catch (Exception e) {
        if (throwException) {
          throw new ConversionException(e);
        } else {
          logger.warning(() -> String.format("Can not convert %s", asString(t)));
        }
      }
      return defaultObject(result, defaultValue);
    };
  }

  @Override
  public boolean isSupportSourceClass(Class<?> sourceClass) {
    return supportedSourceClass.contains(sourceClass)
        || supportedSourceClass.stream().anyMatch(c -> c.isAssignableFrom(sourceClass));
  }

  @Override
  public boolean isSupportTargetClass(Class<?> targetClass) {
    return cached.computeIfAbsent(targetClass,
        t -> Entity.class.isAssignableFrom(t) && JPAUtils.isPersistenceClass(t));
  }

  @PostConstruct
  void onPostConstruct() {
    ConverterRegistry.register(this);
  }

  void onPostContainerStartedEvent(@Observes PostContainerStartedEvent e) {}

  @PreDestroy
  void onPreDestroy() {
    cached.keySet().forEach(c -> ConverterRegistry.deregister(ConverterType.of(Object.class, c)));
  }

  Annotation[] resolveQualifier(Class<?> cls) {
    return puqCached.computeIfAbsent(cls, (c) -> {
      return JPARepositoryExtension.resolveQualifiers(cls);
    });
  }
}
