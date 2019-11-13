package com.emerycprimeau.exception;

public class BlankException extends Exception{
    public String err;
    public BlankException(String m) { err = m; }
}
