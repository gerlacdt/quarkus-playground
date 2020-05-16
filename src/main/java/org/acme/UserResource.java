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
    public GetSingleUserResponse getUser(@PathParam("id") Long id) {
        var u = userService.findById(id);
        return new GetSingleUserResponse(u);
    }

    @GET
    public GetUsersResponse getUsers() {
        var users = userService.findAll();
        return new GetUsersResponse(users);
    }


    @POST
    public Response createUser(GetSingleUserResponse u) {
        var user = userService.save(u.toUser());
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteById(id);
        return Response.ok().build();
    }
}
