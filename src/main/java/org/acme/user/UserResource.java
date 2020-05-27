package org.acme.user;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {
  private static final Logger log = LoggerFactory.getLogger(UserResource.class);

  @Inject UserService userService;

  /**
   * Returns the user with the given id. Or NOT_FOUND.
   *
   * @param id id
   * @return the user
   */
  @GET
  @Path("/{id}")
  public User getUser(@PathParam("id") Long id) {
    var userOptional = userService.findById(id);
    if (userOptional.isPresent()) {
      return userOptional.get();
    }
    throw new NotFoundException(String.format("Given user id %d does not exist.", id));
  }

  /**
   * Returns all users from the database.
   *
   * @return all users
   */
  @GET
  public GetUsersResponse getUsers() {
    var users = userService.findAll();
    var response = new GetUsersResponse();
    response.setUsers(users);
    return response;
  }

  /**
   * Saves the given user data in the database.
   *
   * @param u the json request body
   * @return the stored user with database url
   */
  @POST
  public Response createUser(@Valid User u) {
    var user = userService.save(u);
    return Response.status(Response.Status.CREATED).entity(user).build();
  }

  @PUT
  @Path("/{id}")
  public User updateUser(@Valid User u) {
    return userService.update(u);
  }

  @DELETE
  @Path("/{id}")
  public Response deleteUser(@PathParam("id") Long id) {
    userService.deleteById(id);
    return Response.ok().build();
  }
}
