package com.emerycprimeau.exception;

public class BlankScore extends Exception{
    public String err;
    public BlankScore(String m) { err = m; }
}
