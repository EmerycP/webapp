package com.emerycprimeau;

import javax.ws.rs.*;
import java.util.UUID;

@Path("/") // reçoit tout ce qui est dans /api/
public class WebService {

    public WebService(){

    }

    //  -> /api/hello/paramName
    @GET @Path("hello/{name}")
    public String helloGET(
            @PathParam("name") String nom
            //@QueryParam("option") String option
    ){
        //if (option==nul) throw new IllegalAccessException();
        return "Hello " + nom;
    }

    //  -> /api/hello/paramName
    @POST
    @Path("hello/{name}")
    public String helloPOST(

            @PathParam("name") String nom
            //@QueryParam("option") String option
    ){
        return "Coucou " + nom;
    }

    @POST
    @Path("signin")
    public Token signin(EmailPassword login){
        System.out.println("POST SignIn " + login.email + " " + login.password);
        Token t = new Token();
        t.tokenId = UUID.randomUUID().toString();
        t.userId = UUID.randomUUID().toString();
        return t;
    }

}
