package org.corant.demo.ddd.interfaces;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.corant.demo.ddd.application.command.UserService;
import org.corant.demo.ddd.application.parameter.DeleteUser;
import org.corant.demo.ddd.application.parameter.MaintainUser;
import org.corant.suites.jaxrs.shared.AbstractJaxrsResource;

@Path("/users")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Users extends AbstractJaxrsResource {

  @Inject
  UserService service;

  @Path("/delete/")
  @POST
  public Response delete(DeleteUser cmd) {
    service.delete(cmd);
    return this.ok();
  }

  @Path("/save/")
  @POST
  public Response save(MaintainUser cmd) {
    if (cmd.getId() == null) {
      return this.ok(service.create(cmd));
    } else {
      service.update(cmd);
      return this.ok();
    }
  }
}
