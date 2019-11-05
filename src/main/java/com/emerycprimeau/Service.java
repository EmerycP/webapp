package com.emerycprimeau;

import com.emerycprimeau.model.Game;
import com.emerycprimeau.model.User;

import java.util.ArrayList;
import java.util.List;

public class Service {

    public List<Game> getCompletedGame(User u)
    {

        List<Game> gL = new ArrayList<>();
        for(Game g: u.game)
        {
            if(g.EstCompleter == true)
            {
                gL.add(g);
            }
        }
        return gL;
    }

    public List<Game> getToCompleteGame(User u)
    {

        List<Game> gL = new ArrayList<>();
        for(Game g: u.game)
        {
            if(g.EstCompleter == false)
            {
                gL.add(g);
            }
        }
        return gL;
    }

    public User getUser(int id, List<User> userList)
    {
        for(User u: userList)
        {
            if(u.ID == id)
            {
                return  u;
            }
        }
        return null;
    }
}
