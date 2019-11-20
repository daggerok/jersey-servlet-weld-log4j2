package daggerok;

import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Path("/")
@RequestScoped
public class RestResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Path("/api/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        return Response.ok(Collections.singletonMap("hello", "world!"))
                       .header("X-URL", Optional.ofNullable(uriInfo.getRequestUri())
                                                .map(Objects::toString)
                                                .orElse(""))
                       .build();
    }
}
