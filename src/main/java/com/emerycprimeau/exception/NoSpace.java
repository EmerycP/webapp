package com.emerycprimeau.exception;

public class NoSpace extends Exception{
    public String err;
    public NoSpace(String m) { err = m; }
}
