package daggerok;

import lombok.extern.log4j.Log4j2;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Log4j2
// @Singleton
@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {

    @Context
    Request request;

    @Context
    Response response;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter: {} / {}", request, response);
        log.info(request);
        log.info(response);
        chain.doFilter(request, response);
    }
}
