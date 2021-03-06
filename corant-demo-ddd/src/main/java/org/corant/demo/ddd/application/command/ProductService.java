package org.corant.demo.ddd.application.command;

import static org.corant.shared.util.Assertions.shouldNotNull;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.corant.demo.ddd.application.parameter.DeleteProduct;
import org.corant.demo.ddd.application.parameter.MaintainProduct;
import org.corant.demo.ddd.domain.Product;
import org.corant.demo.ddd.shared.AbstractService;
import org.corant.demo.ddd.ubiquity.Parameter;
import org.corant.shared.ubiquity.Tuple.Triple;
import com.mongodb.client.gridfs.GridFSBucket;

@ApplicationScoped
@Transactional
public class ProductService extends AbstractService {

  @Inject
  @Named("corant-demo-files.fs")
  GridFSBucket fs;

  public Long create(MaintainProduct cmd) {
    Parameter preserveParam = Parameter.empty();
    if (cmd.isNotifyCreated()) {
      preserveParam = preserveParam.withAttribute("notifyCreated", true);
    }
    return new Product(cmd.getNumber(), cmd.getName(), cmd.getRemark(),
        cmd.getAttributes().stream().map(it -> Triple.of(it.getName(), it.getType(), it.getValue()))
            .collect(Collectors.toList())).preserve(preserveParam, null).getId();
  }

  public void delete(DeleteProduct cmd) {
    shouldNotNull(repo.get(Product.class, cmd.getId()), IllegalArgumentException::new)
        .destroy(Parameter.empty(), null);
  }

  public void update(MaintainProduct cmd) {
    shouldNotNull(repo.get(Product.class, cmd.getId()), IllegalArgumentException::new)
        .changeName(cmd.getName()).changeNumber(cmd.getRemark()).changeRemark(cmd.getRemark())
        .changeAttributes(cmd.getAttributes().stream()
            .map(it -> Triple.of(it.getName(), it.getType(), it.getValue()))
            .collect(Collectors.toList()));
  }

}
