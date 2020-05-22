package org.corant.demo.ddd.interfaces;

import static org.corant.shared.util.MapUtils.mapOf;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.corant.demo.ddd.application.commad.OrderService;
import org.corant.demo.ddd.application.parameter.ConfirmOrder;
import org.corant.demo.ddd.application.parameter.DeleteOrder;
import org.corant.demo.ddd.application.parameter.DeliveryOrder;
import org.corant.demo.ddd.application.parameter.MaintainOrder;
import org.corant.demo.ddd.application.parameter.PayOrder;
import org.corant.demo.ddd.application.parameter.QueryOrder;
import org.corant.demo.ddd.application.query.OrderQueryService;
import org.corant.suites.jaxrs.shared.AbstractJaxrsResource;
import org.corant.suites.query.shared.QueryParameter.GenericQueryParameter;

@Path("/orders")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Orders extends AbstractJaxrsResource {

  @Inject
  OrderService service;

  @Inject
  OrderQueryService querySerivice;

  @Path("/confirm/")
  @POST
  public Response confirm(ConfirmOrder cmd) {
    service.confirm(cmd);
    return this.ok();
  }

  @Path("/delete/")
  @POST
  public Response delete(DeleteOrder cmd) {
    service.delete(cmd);
    return this.ok();
  }

  @Path("/delivery/")
  @POST
  public Response delivery(DeliveryOrder cmd) {
    service.delivery(cmd);
    return this.ok();
  }

  @Path("/get/{id}/")
  @GET
  public Response get(@PathParam("id") String id) {
    return this.ok(querySerivice.get(mapOf("id", id)));
  }

  @Path("/pay/")
  @POST
  public Response pay(PayOrder cmd) {
    service.pay(cmd);
    return this.ok();
  }

  @Path("/query/")
  @POST
  public Response query(GenericQueryParameter<QueryOrder> param) {
    return this.ok(querySerivice.pageSelect(param));
  }

  @Path("/save/")
  @POST
  public Response save(MaintainOrder cmd) {
    if (cmd.getId() == null) {
      return this.ok(service.create(cmd));
    } else {
      service.update(cmd);
      return this.ok();
    }
  }
}
