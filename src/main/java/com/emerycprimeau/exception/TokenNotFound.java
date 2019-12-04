package com.emerycprimeau.exception;

public class TokenNotFound extends Exception{
    public String err;
    public TokenNotFound(String m) { err = m; }
}
