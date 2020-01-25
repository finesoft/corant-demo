package org.corant.demo.ddd.application;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.corant.suites.query.shared.declarative.DeclarativeQueryService;
import org.corant.suites.query.shared.mapping.Query.QueryType;

@DeclarativeQueryService(qualifier = "ro:MYSQL", type = QueryType.SQL)
public interface OrderQueryService {

  @Transactional
  default List<Map<String, Object>> bingo() {
    return select(null);
  }

  List<Map<String, Object>> select(Object parameter);

}
