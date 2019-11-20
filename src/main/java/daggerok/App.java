package daggerok;

import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.ws.rs.ApplicationPath;
import java.net.URI;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.Optional;
import java.util.logging.LogManager;

import static java.time.ZonedDateTime.now;

@Log4j2
@Singleton
@ApplicationPath("")
public class App extends ResourceConfig {

    public App() {
        // register(org.glassfish.jersey.jackson.JacksonFeature.withExceptionMappers());
        // addProperties(Collections.singletonMap("ololo", "trololo"));
        packages(true, App.class.getPackage().getName());
    }

    public static void main(String[] args) {
        setupLog4j2JulAdapter();

        ZonedDateTime from = now();
        Weld builder = new Weld().disableDiscovery()
                                 .addPackages(true, App.class)
                                 // .addPackages(JacksonJsonProvider.class)
                ;

        try (WeldContainer container = builder.initialize()) {
            WebappContext ctx = new WebappContext("GrizzlyJerseyWebappContext", "/");

            val servlet = ctx.addServlet("GrizzlyJerseyServletContainer", ServletContainer.class);
            servlet.setInitParameter("jersey.config.server.provider.packages", App.class.getPackage().getName());
            servlet.setAsyncSupported(true);
            servlet.addMapping("/*");

            MyFilter filter = container.select(MyFilter.class).get();
            FilterRegistration filterRegistration = ctx.addFilter(MyFilter.class.getName(), filter);
            filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), "/*");
            filterRegistration.setAsyncSupported(true);

            ClassLoader classLoader = Optional.ofNullable(ctx.getClassLoader())
                                              .orElse(ClassLoader.getSystemClassLoader());
            App resourceConfig = container.select(App.class).get();
            resourceConfig.setClassLoader(classLoader);

            URI uri = URI.create("http://0.0.0.0:8080");
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (server.isStarted()) server.shutdownNow();
                if (container.isRunning()) container.shutdown();
            }));

            Try.run(server::start)
               .andThenTry(() -> ctx.deploy(server))
               .onFailure(log::error)
               .onSuccess(aVoid -> log.info("Webapp server {} started in {} s",
                                            uri, Duration.between(from, now()).toMillis() / 1000.0f));
        }
    }

    private static void setupLog4j2JulAdapter() {
        String cn = org.apache.logging.log4j.jul.LogManager.class.getName();
        System.setProperty("java.util.logging.manager", cn);
        LogManager jul = LogManager.getLogManager();
        if (cn.equals(jul.getClass().getName())) return;
        Try.run(() -> ClassLoader.getSystemClassLoader().loadClass(cn))
           .onSuccess(v -> log.info("using {} logging manager", LogManager.getLogManager()))
           .onSuccess(v -> {
               if (cn.equals(LogManager.getLogManager().getClass().getName())) return;
               log.warn("please use log4j-jul logging manager adapter!");
               log.warn("java -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager ...");
           })
           .onFailure(e -> log.error("cannot load log4j2-jul logging manager adapter!"));
    }
}
