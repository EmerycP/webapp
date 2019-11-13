package com.emerycprimeau.exception;

public class MaxLength extends Exception{
    public String err;
    public MaxLength(String m) { err = m; }
}
