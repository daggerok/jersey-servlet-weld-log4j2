package daggerok;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class JacksonProvider extends JacksonJsonProvider { }
