package com.emerycprimeau;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/") // reçoit tout ce qui est dans /api/
public class WebService {

    public WebService(){

    }

    //  -> /api/hello/paramName
    @GET @Path("hello/{name}")
    public String hello(
            @PathParam("name") String nom
            //@QueryParam("option") String option
    ){
        return "Hello " + nom;
    }

}
