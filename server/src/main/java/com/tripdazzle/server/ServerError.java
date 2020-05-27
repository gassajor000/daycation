package com.tripdazzle.server;

public class ServerError extends Exception {

    public ServerError(Exception e) {
        super(e);
    }

    public ServerError(String s){
        super(s);
    }
}
