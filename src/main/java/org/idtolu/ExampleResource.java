package org.idtolu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NotRequiredToken
    public String hello() {
    	System.out.println("iniciando hello");
        return "Hello from RESTEasy Reactive";
    }
}