package org.acme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @GET
    public GetUsersResponse getUsers() {
        var users = userService.findAll();
        var response = new GetUsersResponse();
        response.setUsers(users);
        return response;
    }

    @POST
    public Response createUser(User u) {
        var user = userService.save(u);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    public User updateUser(User u) {
        return userService.update(u);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteById(id);
        return Response.ok().build();
    }
}
