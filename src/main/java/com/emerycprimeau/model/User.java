package com.emerycprimeau.model;

import com.emerycprimeau.transfer.GameCompletedResponse;
import com.emerycprimeau.transfer.GameToCompleteResponse;
import com.emerycprimeau.transfer.LoginRequest;

import java.util.List;

public class User extends LoginRequest {
    public int ID;
    public List<Game> game;
}
