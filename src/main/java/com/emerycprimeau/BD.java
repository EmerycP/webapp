package com.emerycprimeau;

import com.emerycprimeau.exception.*;
import com.emerycprimeau.model.Game;
import com.emerycprimeau.model.Token;
import com.emerycprimeau.model.User;
import com.emerycprimeau.transfer.*;
import com.google.gson.Gson;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;

public class BD {

    public static List<User> listUser = new ArrayList<>();
    public static List<Token> listToken = new ArrayList<>();
    Service s = new Service();
    private static int idUser = 0;
    private static int idGame = 0;

    public static final String Cookie = "gameQ-Cookie";

    //date
    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
    String date = format.format(Date.parse(Calendar.getInstance().getTime().toString()));

    //private static List<Game> listUser;

    public BD ()
    {}


    //    public void InitUsers() throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException, NoSpace {
//
//        SignupRequest u1 = new SignupRequest();
//        u1.user = "u1";
//        u1.password = "password";
//        LoginResponse temp = null;
//        try {
//            temp = CreateUser(u1);
//        } catch (UsernameExist | NoSpace | MaxLength | BlankException usernameExist) {
//            usernameExist.printStackTrace();
//        }
//        toAdd(temp.Id,new Game( "The Last of Us Part I", 96, true ));
//        toAdd(temp.Id,new Game("The Surge 2", 79, true));
//        toAdd(temp.Id,new Game("Call of Duty: Modern Warfare", 0, false));
//        toAdd(temp.Id,new Game("Doom Eternal", 80, true));
//        toAdd(temp.Id,new Game("Destiny 2", 0, false));
//
//        SignupRequest u2 = new SignupRequest();
//        u2.user = "u2";
//        u2.password = "password";
//        try {
//            CreateUser(u2);
//        } catch (UsernameExist | NoSpace | MaxLength | BlankException usernameExist) {
//            usernameExist.printStackTrace();
//        }
//
//        SignupRequest u3 = new SignupRequest();
//        u3.user = "u3";
//        u3.password = "password";
//        try {
//            CreateUser(u3);
//        } catch (UsernameExist | NoSpace | BlankException | MaxLength usernameExist) {
//            usernameExist.printStackTrace();
//        }
//
//    }

    public Response CreateUser(SignupRequest req) throws UsernameExist, BlankException, MaxLength, NoSpace {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // valider tous les champs
        if(req.user.equals("") || req.password.equals(""))
            throw new BlankException("BlankE");

        int count = req.user.length();
        String userN = req.user.replaceAll(" ", "");
        if(count != userN.length())
            throw new NoSpace("S");
        int countP = req.password.length();
        String pass = req.password.replaceAll(" ", "");
        if(countP != pass.length())
            throw new NoSpace("S");



        if(req.user.length() >= 20 || req.password.length() >= 20)
            throw new MaxLength("Max");



        // check existing user
        for(User u: listUser)
            if(u.user.equals(req.user))
                throw new UsernameExist("User Exception");

        // create user
        User u = new User();
        u.user = req.user;
        u.password = req.password;
        u.game = new ArrayList<>();
        u.ID = idUser++;
        String token = UUID.randomUUID().toString();
        NewCookie nC = new NewCookie(Cookie, token, "/","", "id token", 604800, true);

        listToken.add(new Token(token, u.ID, 604800));
        System.out.println(token);

        // add
        listUser.add(u);

        //connect
        LoginResponse s = new LoginResponse();
        s.Id = u.ID;
        s.emailCleaned = u.user;

        return Response.ok(new Gson().toJson(s), MediaType.APPLICATION_JSON).cookie(nC).build();
    }


    public Response toLogin (LoginRequest lR) throws NoMatch, BlankException {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(lR.user.equals("") || lR.password.equals(""))
            throw new BlankException("Blank");


        LoginResponse lRes = new LoginResponse();
        for (User s: listUser)
            if(s.user.equals(lR.user) && s.password.equals(lR.password))
            {
                lRes.Id = s.ID;
                lRes.emailCleaned = s.user;
                String token = UUID.randomUUID().toString();
                NewCookie nC = new NewCookie(Cookie, token, "/","", "id token", 604800, true);
                listToken.add(new Token(token, s.ID, 604800));
                return Response.ok(new Gson().toJson(lRes), MediaType.APPLICATION_JSON).cookie(nC).build();
            }

        throw new NoMatch("NM");
    }

    public List<Game> getToCompleteList (int gR)
    {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (User u: listUser)
        {
            if(u.ID == gR)
            {

                return s.getToCompleteGame(u);

            }
        }
        return null;
    }

    public List<Game> getCompletedList (int gR)
    {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (User u: listUser)
        {
            if(u.ID == gR)
            {

                return s.getCompletedGame(u);

            }
        }
        return null;
    }


    public boolean toAdd(int userId, Game game) throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException, NoSpace {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // valider tous les champs

        game.Name = game.Name.trim();

        if(!(userId >= 0))
            throw new NoUserConnected("NoU");

        if(game.Name.equals(""))
            throw new BlankException("BE");

        if(game.Score == null )
            throw new BlankScore("BE");


        // check existing game
        for (Game g: s.getUser(userId, listUser).game) {
            if(game.Name.equals(g.Name))
                throw new GameExist("GE");
        }

        if(game.Name.trim().length() > 80)
            throw new MaxLength("ML");

        if(game.Score < 0 || game.Score > 100)
            throw new Score("S");

        try {
            Integer.parseInt(game.Score.toString());
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }


        if(game.EstCompleter)
            s.getUser(userId, listUser).game.add(new Game(idGame++,date,game.Name,game.Score,game.EstCompleter));
        else
            s.getUser(userId, listUser).game.add(new Game(idGame++,date,game.Name,0,game.EstCompleter));


        return true;
    }

    public Game toEdit (int gameId, int userId) throws GameSelectedDontExist, NoUserConnected {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Game g: s.getUser(userId, listUser).game)
        {
            if(!(userId >= 0))
                throw new NoUserConnected("NoU");
            if(g.ID == gameId)
            {
                return g;
            }
        }
        throw new GameSelectedDontExist("GSDE");
    }

    public Boolean gameEdit(GameRequestEdit g) throws NoUserConnected, GameExist, Score, MaxLength, BlankScore, BlankException {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        g.name = g.name.trim();

        //faire de la vérification
        if(!(g.userID >= 0))
            throw new NoUserConnected("NoU");



        //le créer
        for (Game game: s.getUser(g.userID, listUser).game)
        {

            if(game.ID == g.gameID)
            {

                if(g.name.equals(""))
                    throw new BlankException("BE");
                if(g.score < 0 || g.score > 100)
                    throw new Score("S");

                if(g.name.trim().length() > 80)
                    throw new MaxLength("ML");

                if(g.score == null )
                    throw new BlankScore("BE");

                try {
                    Integer.parseInt(g.score.toString());
                } catch(NumberFormatException e) {
                    e.printStackTrace();
                }

                for (Game game2: s.getUser(g.userID, listUser).game)
                {
                    if(game.ID != game2.ID && game.Name.equals(game2.Name))
                        throw new GameExist("GE");
                    else
                        game.Name = g.name;
                }
                game.date = date;
                game.EstCompleter = g.estComplete;
                if(g.estComplete)
                    game.Score = g.score;
                else
                    game.Score = 0;
                return true;

            }
        }
        return false;


    }




}
