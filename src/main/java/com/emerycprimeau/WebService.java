package com.emerycprimeau;

import com.emerycprimeau.exception.*;
import com.emerycprimeau.model.Game;
import com.emerycprimeau.model.Token;
import com.emerycprimeau.model.User;
import com.emerycprimeau.transfer.*;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
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
        //bd.InitUsers();
    }


    @POST
    @Path("login")
    public Response toLogIn (LoginRequest logR) throws NoMatch, BlankException {
        System.out.println("LogIn -> " + logR.user + " " + logR.password);
        return bd.toLogin(logR);
    }

    @POST
    @Path("signup")
    public Response toSignUp (SignupRequest logR) throws UsernameExist, BlankException, MaxLength, NoSpace {
        System.out.println("SignIn -> " + logR.user + " " + logR.password);
        return bd.CreateUser(logR);
    }

    @GET
    @Path("gameToComplete")
    public List<Game> getToCompleteList (@CookieParam(BD.Cookie) Cookie cookie) throws TokenNotFound {
        for(Token u: BD.listToken)
        {
            if(cookie.getValue().equals(u.token)) {
                System.out.println("getToComplete -> " + u.userId);
                return bd.getToCompleteList(u.userId);
            }
        }

        throw new TokenNotFound("TNF");
    }

    @GET
    @Path("gameCompleted")
    public List<Game> getCompletedList (@CookieParam(BD.Cookie) Cookie cookie) throws TokenNotFound {
        for(Token u: BD.listToken)
        {
            if(cookie.getValue().equals(u.token)) {
                System.out.println("getToComplete -> " + u.userId);
                return bd.getCompletedList(u.userId);
            }
        }
        throw new TokenNotFound("TNF");
    }

    @GET
    @Path("logout")
    public Response toLogOut (@CookieParam(BD.Cookie)Cookie cookie) throws NoUserConnected, TokenNotFound {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Token u: BD.listToken)
        {
            if(cookie.getValue().equals(u.token))
            {
                System.out.println(u.token + " " + u.userId);
                BD.listToken.remove(u);
                NewCookie aSupprimer = new NewCookie(BD.Cookie, null, "/", null, null, 0, true);
                return Response.ok(new Gson().toJson(true), MediaType.APPLICATION_JSON).cookie(aSupprimer).build();
            }
        }
        throw new TokenNotFound("NoU");
    }

    @POST
    @Path("GameAdded")
    public boolean toAdd(Game game, @CookieParam(BD.Cookie) Cookie cookie) throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException, NoSpace {

        for(Token u: BD.listToken)
        {
            if(cookie.getValue().equals(u.token)) {
                System.out.println("Ajout du jeu " + game.Name + " à la liste du user à l'index " + u.userId);
                return bd.toAdd(u.userId, game);
            }
        }
        return false;
    }

    @GET
    @Path("toEdit/{gameId}")
    public Game toEdit (@PathParam("gameId") int gameId, @CookieParam(BD.Cookie) Cookie cookie) throws GameSelectedDontExist, NoUserConnected, TokenNotFound {
        for(Token u: BD.listToken)
        {
            if(cookie.getValue().equals(u.token)) {
                System.out.println("Getting the game "+ bd.toEdit(gameId, u.userId).Name + " with id -> " + gameId + " of user " + u.userId);

                return  bd.toEdit(gameId, u.userId);
            }
        }
        throw new TokenNotFound("TNF");
    }

    @POST
    @Path("GameEdited")
    public Boolean gameEdit (GameRequestEdit g, @CookieParam(BD.Cookie) Cookie cookie) throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException {
        for(Token u: BD.listToken) {
            if (cookie.getValue().equals(u.token)) {
                System.out.println("Le jeu " + g.name + " a été modifié");
                return bd.gameEdit(g);
            }
        }
        return false;
    }


}
