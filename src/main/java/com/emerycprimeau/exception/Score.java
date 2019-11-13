package com.emerycprimeau.exception;

public class Score extends Exception{
    public String err;
    public Score(String m) { err = m; }
}
