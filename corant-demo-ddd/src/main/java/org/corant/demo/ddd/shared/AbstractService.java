package org.corant.demo.ddd.shared;

import java.math.BigDecimal;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.corant.demo.ddd.domain.Product;
import org.corant.demo.ddd.domain.User;
import org.corant.demo.ddd.ubiquity.Commodity;
import org.corant.demo.ddd.ubiquity.Participator;
import org.corant.suites.ddd.repository.JPARepository;

@ApplicationScoped
public abstract class AbstractService {

  @Inject
  protected JPARepository repo;

  protected Commodity getCommodity(Long id, BigDecimal price) {
    if (id != null) {
      return repo.get(Product.class, id).asCommodity(price);
    }
    return null;
  }

  protected Participator getParticipator(Long id) {
    if (id != null) {
      return repo.get(User.class, id).asParticipator();
    }
    return null;
  }
}
