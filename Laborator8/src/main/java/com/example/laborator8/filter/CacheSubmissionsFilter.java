package com.example.laborator8.filter;


import com.example.laborator8.model.Evaluation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@ApplicationScoped
public class CacheSubmissionsFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = Logger.getLogger(CacheSubmissionsFilter.class.getName());
    private List<Evaluation> evaluationsCache = new ArrayList<>();
    private boolean isCachePopulated = false;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String method = requestContext.getMethod();
        URI requestUri = requestContext.getUriInfo().getRequestUri();
        LOGGER.log(Level.INFO, "LOGGER: Intercepted {0} request for URI: {1}", new Object[]{method, requestUri});

        String getAllEndpoint = "/evaluations";
        if (requestUri.getPath().endsWith(getAllEndpoint)) {
            if ("GET".equalsIgnoreCase(method) && responseContext.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                if (!isCachePopulated) {
                    LOGGER.log(Level.INFO, "LOGGER: Cache is empty. Attempting to populate cache.");
                    Object entity = responseContext.getEntity();
                    if (entity instanceof List<?>) {
                        evaluationsCache = new ArrayList<>((List<Evaluation>) entity);
                        isCachePopulated = true;
                        LOGGER.log(Level.INFO, "LOGGER: Cache populated with {0} evaluations entries.", evaluationsCache.size());
                    } else {
                        LOGGER.log(Level.WARNING, "LOGGER: Expected entity is not of type List<Evaluations>.");
                    }
                } else {
                    LOGGER.log(Level.INFO, "LOGGER: Serving response from cache.");
                    responseContext.setEntity(evaluationsCache, null, MediaType.APPLICATION_JSON_TYPE);
                }
            }
        }

        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            LOGGER.log(Level.INFO, "LOGGER: Modification request detected. Invalidating cache.");
            evaluationsCache.clear();
            isCachePopulated = false;
        }
    }

}