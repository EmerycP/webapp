package com.emerycprimeau.exception;

public class GameExist extends Exception{
    public String err;
    public GameExist(String m) { err = m; }
}
