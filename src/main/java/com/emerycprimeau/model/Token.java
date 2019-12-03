package com.emerycprimeau.model;

public class Token {
    public String token;
    public int userId;
    public int expiration;

    public Token(String tk, int id, int exp)
    {
        token = tk;
        userId = id;
        expiration = exp;
    }
}
