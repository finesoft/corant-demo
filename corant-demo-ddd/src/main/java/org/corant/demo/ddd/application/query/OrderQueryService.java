package org.corant.demo.ddd.application.query;

import java.util.List;
import java.util.Map;
import org.corant.suites.query.shared.QueryService.Paging;
import org.corant.suites.query.shared.QueryService.QueryWay;
import org.corant.suites.query.shared.declarative.DeclarativeQueryService;
import org.corant.suites.query.shared.declarative.QueryMethod;
import org.corant.suites.query.shared.mapping.Query.QueryType;

@DeclarativeQueryService(qualifier = "ro:MYSQL", type = QueryType.SQL)
public interface OrderQueryService {

  Map<String, Object> get(Object parameter);

  @QueryMethod(name = "OrderQueryService.select", way = QueryWay.PAGE)
  Paging<Map<String, Object>> pageSelect(Object parameter);

  List<Map<String, Object>> select(Object parameter);

}
