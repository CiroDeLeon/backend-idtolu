package org.idtolu;

import java.lang.annotation.Retention;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.idtolu.entity.User;
import org.idtolu.repository.UserRepository;

import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface NotRequiredToken {
}

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Inject
    UserRepository repo;
    
    @Context
    private ResourceInfo resourceInfo;


    @Override
    public void filter(ContainerRequestContext requestContext) {
    	Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(NotRequiredToken.class)) {
            return;
        }
    	
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN)
                    .entity("Authorization header must be provided").build());
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        String username=JWT.decode(token)
                .getSubject();
        
        User user = repo.findByUsername(username);
        if (user == null) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN)
                    .entity("Invalid token").build());
        }

        try {
            JWT.require(Algorithm.HMAC256(user.getUsername()))
                    .build()
                    .verify(token)
                    .getSubject();

            

            
        } catch (Exception e) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN)
                    .entity("Invalid token").build());
        }
    }
}


