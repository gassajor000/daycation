package com.tripdazzle.server;

public class DatabaseError extends ServerError {

    public DatabaseError(Exception e) {
        super(e);
    }

    public DatabaseError(String s) {
        super(s);
    }
}
