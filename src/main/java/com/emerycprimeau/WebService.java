package com.emerycprimeau;

import com.emerycprimeau.model.User;
import com.emerycprimeau.transfer.LoginRequest;
import com.emerycprimeau.transfer.LoginResponse;
import com.emerycprimeau.transfer.SignupRequest;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("/") // reçoit tout ce qui est dans /api/
public class WebService {

    private BD bd = new BD();
    public WebService(){

    }

    //region Exemple

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
    @Path("null")
    public LoginResponse toLogin(LoginRequest login){
        System.out.println("POST SignIn " + login.email + " " + login.password);
        LoginResponse t = new LoginResponse();
        t.Id = Integer.parseInt(UUID.randomUUID().toString());
        t.emailCleaned = login.email;
        return t;
    }

    //endregion


    // --------------------- Code ------------------------- //

    @POST
    @Path("init")
    public void toInit (){
        bd.InitUsers();
    }


    @POST
    @Path("signup")
    public LoginResponse toSignUp (SignupRequest logR){
        System.out.println("SignIn -> " + logR.email + " " + logR.password);
        return bd.CreateUser(logR);
    }


    @POST
    @Path("login")
    public LoginResponse toLogIn (LoginRequest logR){
        System.out.println("LogIn -> " + logR.email + " " + logR.password);
        return bd.toLogin(logR);
    }



}
