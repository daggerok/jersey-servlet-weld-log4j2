package daggerok;

import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Optional;

@Log4j2
@Path("")
@RequestScoped
public class MvcIndexPage {

    @GET
    public Response root() {
        InputStream indexHtml = getClass().getResourceAsStream("/templates/index.html");
        return Optional.ofNullable(indexHtml)
                       .map(is -> Response.ok()
                                          .entity(is)
                                          .type(MediaType.TEXT_HTML))
                       .orElse(Response.status(Response.Status.BAD_REQUEST)
                                       .type(MediaType.APPLICATION_JSON))
                       .build();
    }

    @GET
    @Path("/index.html")
    public Response indexHtml() {
        InputStream indexHtml = getClass().getResourceAsStream("/templates/index.html");
        return Optional.ofNullable(indexHtml)
                       .map(is -> Response.ok()
                                          .entity(is)
                                          .type(MediaType.TEXT_HTML))
                       .orElse(Response.status(Response.Status.BAD_REQUEST)
                                       .type(MediaType.APPLICATION_JSON))
                       .build();
    }
}
