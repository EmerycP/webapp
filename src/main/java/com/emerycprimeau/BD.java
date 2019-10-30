package com.emerycprimeau;

import com.emerycprimeau.model.User;
import com.emerycprimeau.transfer.GameCompletedResponse;
import com.emerycprimeau.transfer.LoginRequest;
import com.emerycprimeau.transfer.LoginResponse;
import com.emerycprimeau.transfer.SignupRequest;

import java.text.SimpleDateFormat;
import java.util.*;

public class BD {

    private static List<User> listUser = new ArrayList<>();
    private static int idUser = 0;
    private static int currentUser;

    //private static List<Game> listUser;

    public BD ()
    {}


    public void InitUsers()
    {

        SignupRequest u1 = new SignupRequest();
        u1.email = "u1@gmail.com";
        u1.password = "password";
        CreateUser(u1);

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
        u.gameCompleted = new ArrayList<>();
        u.game2Complete = new ArrayList<>();
        u.ID = idUser++;

        // add and connect
        listUser.add(u);
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
}
