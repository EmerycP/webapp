package com.emerycprimeau.exception;

public class GameSelectedDontExist extends Exception{
    public String err;
    public GameSelectedDontExist(String m) { err = m; }
}
