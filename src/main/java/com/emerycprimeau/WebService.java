package com.emerycprimeau;

import com.emerycprimeau.exception.*;
import com.emerycprimeau.model.Game;
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
        System.out.println("POST SignIn " + login.user + " " + login.password);
        LoginResponse t = new LoginResponse();
        t.Id = Integer.parseInt(UUID.randomUUID().toString());
        t.emailCleaned = login.user;
        return t;
    }

    //endregion


    // --------------------- Code ------------------------- //

    @POST
    @Path("init")
    public void toInit () throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException, NoSpace {
        System.out.println("Init complété!");
        bd.InitUsers();
    }


    @POST
    @Path("login")
    public LoginResponse toLogIn (LoginRequest logR) throws NoMatch, BlankException {
        System.out.println("LogIn -> " + logR.user + " " + logR.password);
        return bd.toLogin(logR);
    }

    @POST
    @Path("signup")
    public LoginResponse toSignUp (SignupRequest logR) throws UsernameExist, BlankException, MaxLength, NoSpace {
        System.out.println("SignIn -> " + logR.user + " " + logR.password);
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
    public boolean toLogOut (LogoutRequest lR) throws NoUserConnected {
        System.out.println("logOut de l'utilisateur à l'index " + lR.userID);
        return bd.toLogOut(lR);
    }

    @POST
    @Path("GameAdded/{userId}")
    public boolean toAdd(@PathParam("userId") int userId, Game game) throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException, NoSpace {
        System.out.println("Ajout du jeu " + game.Name + " à la liste du user à l'index " + userId);
        return bd.toAdd(userId, game);
    }

    @GET
    @Path("toEdit/{userId}/{gameId}")
    public Game toEdit (@PathParam("userId") int userId, @PathParam("gameId") int gameId) throws GameSelectedDontExist, NoUserConnected {
        System.out.println("Getting the game "+ bd.toEdit(gameId, userId).Name + " with id -> " + gameId + " of user " + userId);
        return  bd.toEdit(gameId, userId);
    }

    @POST
    @Path("GameEdited")
    public Boolean gameEdit (GameRequestEdit g) throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException {
        System.out.println("Le jeu " + g.name + " a été modifié");
        return bd.gameEdit(g);
    }


}
