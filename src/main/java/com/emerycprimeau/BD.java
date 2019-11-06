package com.emerycprimeau;

import com.emerycprimeau.model.Game;
import com.emerycprimeau.model.User;
import com.emerycprimeau.transfer.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class BD {

    private static List<User> listUser = new ArrayList<>();
    Service s = new Service();
    private static int idUser = 0;

    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
    String date = format.format(Date.parse(Calendar.getInstance().getTime().toString()));

    //private static List<Game> listUser;

    public BD ()
    {}


    public void InitUsers()
    {

        SignupRequest u1 = new SignupRequest();
        u1.email = "u1@gmail.com";
        u1.password = "password";
        LoginResponse temp = CreateUser(u1);
        s.getUser(temp.Id, listUser).game.add(new Game(1, date, "The Last of Us Part I", 96, true));
        s.getUser(temp.Id, listUser).game.add(new Game(2, date, "The Surge 2", 79, true));
        s.getUser(temp.Id,listUser).game.add(new Game(3, date, "Call of Duty: Modern Warfare", 0, false));
        s.getUser(temp.Id,listUser).game.add(new Game(4, date, "Doom Eternal", 80, true));
        s.getUser(temp.Id,listUser).game.add(new Game(5, date, "Destiny 2", 0, false));


        SignupRequest u2 = new SignupRequest();
        u2.email = "u2@gmail.com";
        u2.password = "password";
        CreateUser(u2);

        SignupRequest u3 = new SignupRequest();
        u3.email = "u3@gmail.com";
        u3.password = "password";
        CreateUser(u3);

    }

    public LoginResponse CreateUser(SignupRequest req)
    {
        // valider tous les champs





        // check existing email
        for(User u: listUser)
            if(u.email.equals(req.email))
                throw new IllegalArgumentException();

        // create user
        User u = new User();
        u.email = req.email.toLowerCase();
        u.password = req.password;
        u.game = new ArrayList<>();
        u.ID = idUser++;

        // add
        listUser.add(u);

        //connect
        LoginResponse s = new LoginResponse();
        s.Id = u.ID;
        s.emailCleaned = u.email;

        return s;
    }


    public LoginResponse toLogin (LoginRequest lR)
    {
        LoginResponse lRes = new LoginResponse();
        for (User s: listUser)
            if(s.email.equals(lR.email) && s.password.equals(lR.password))
            {
                lRes.Id = s.ID;
                lRes.emailCleaned = s.email.toLowerCase();
                return lRes;
            }

        throw new NullPointerException();
    }

    public boolean toLogOut (LogoutRequest lR)
    {
        if(s.getUser(lR.userID, listUser).ID == lR.userID)
            return true;
        throw new NullPointerException();
    }

    public List<Game> getToCompleteList (int gR)
    {

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

        for (User u: listUser)
        {
            if(u.ID == gR)
            {

                return s.getCompletedGame(u);

            }
        }
        return null;
    }




}
