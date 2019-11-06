package com.emerycprimeau;

import com.emerycprimeau.model.Game;
import com.emerycprimeau.model.User;
import com.emerycprimeau.transfer.*;

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
        System.out.println("Init complété!");
        bd.InitUsers();
    }


    @POST
    @Path("login")
    public LoginResponse toLogIn (LoginRequest logR){
        System.out.println("LogIn -> " + logR.email + " " + logR.password);
        return bd.toLogin(logR);
    }

    @POST
    @Path("signup")
    public LoginResponse toSignUp (SignupRequest logR){
        System.out.println("SignIn -> " + logR.email + " " + logR.password);
        return bd.CreateUser(logR);
    }

    @GET
    @Path("gameToComplete/{userId}")
    public List<Game> getToCompleteList (@PathParam("userId") int gR){
        System.out.println("getToComplete -> " + gR);
        return bd.getToCompleteList(gR);
    }

    @GET
    @Path("gameCompleted/{userId}")
    public List<Game> getCompletedList (@PathParam("userId") int gR){
        System.out.println("getCompleted -> userId:" + gR);
        return bd.getCompletedList(gR);
    }

    @POST
    @Path("logout")
    public boolean toLogOut (LogoutRequest lR)
    {
        System.out.println("logOut de l'utilisateur à l'index " + lR.userID);
        return bd.toLogOut(lR);
    }

    @POST
    @Path("GameAdded/{userId}")
    public boolean toAdd(@PathParam("userId") int userId, Game game)
    {
        System.out.println("Ajout du jeu " + game.Name + " à la liste du user à l'index " + userId);
        return bd.toAdd(userId, game);
    }


}
