package daggerok;

import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Provider
public class Fallback implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        String error = Optional.ofNullable(exception)
                               .map(Exception::getLocalizedMessage)
                               .orElse("empty");
        Map<String, String> payload = Collections.singletonMap("error", error);
        log.error("{}", payload, exception);
        return Response.status(Response.Status.BAD_REQUEST)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(payload)
                       .build();
    }
}
