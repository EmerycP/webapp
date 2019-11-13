package com.emerycprimeau.exception;

public class NoUserConnected extends Exception{
    public String err;
    public NoUserConnected(String m) { err = m; }
}
