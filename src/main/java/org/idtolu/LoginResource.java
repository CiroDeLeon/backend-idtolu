package org.idtolu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.idtolu.dto.Credentials;
import org.idtolu.dto.LoginResponse;
import org.idtolu.entity.User;
import org.idtolu.service.LoginService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;




@Path("/auth")
public class LoginResource {

    @Inject    
    LoginService loginService;
    
    @Inject
    Validator validator;
    
    private List<String> validate(Credentials credentials) {
    	  List<String> errorMessages = new ArrayList<>();
    	  Set<ConstraintViolation<Credentials>> violations = validator.validate(credentials);
    	  for (ConstraintViolation<Credentials> violation : violations) {
    	    errorMessages.add(violation.getMessage());
    	  }
    	  return errorMessages;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NotRequiredToken
    public Response login(Credentials credentials) {
    	
    	List<String> errorMessages = validate(credentials);

    	if (!errorMessages.isEmpty()) {
    	    return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
    	}

        try {
            User user = loginService.login(credentials);
            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 864000000))                    
                    .sign(Algorithm.HMAC256(user.getUsername()));
            return Response.ok(new LoginResponse(token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NotRequiredToken
    public Response signup(Credentials credentials) {
    	List<String> errorMessages = validate(credentials);

    	if (!errorMessages.isEmpty()) {
    	    return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
    	}
    	
        try {
            User user = loginService.signup(credentials);            
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @POST
    @Path("/reset-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetPassword(Credentials credentials) {
    	List<String> errorMessages = validate(credentials);

    	if (!errorMessages.isEmpty()) {
    	    return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
    	}
        try {
            User user = loginService.resetPassword(credentials);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    
}
