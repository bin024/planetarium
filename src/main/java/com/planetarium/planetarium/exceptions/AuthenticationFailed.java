package com.planetarium.planetarium.exceptions;

public class AuthenticationFailed extends RuntimeException {
    public AuthenticationFailed(String message){
        super(message);
    }
    
}
