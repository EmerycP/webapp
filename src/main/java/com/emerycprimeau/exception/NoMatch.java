package com.emerycprimeau.exception;

public class NoMatch extends Exception{
    public String err;
    public NoMatch(String m) { err = m; }
}
