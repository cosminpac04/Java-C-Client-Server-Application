package motorcycle.rest;

import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import java.io.IOException;

@ApplicationPath("/api")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(RaceResource.class);
        // Enable CORS
        register(CorsFilter.class);
    }
}

@Provider
@PreMatching
class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
