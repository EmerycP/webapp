package com.emerycprimeau.exception;

public class UsernameExist extends Exception{
    public String err;
    public UsernameExist(String m) { err = m; }
}
