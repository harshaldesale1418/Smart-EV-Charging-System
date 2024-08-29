package com.evcharging.station.RuntimeException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException extends RuntimeException{
    private String message;
    private String resource;
    public AuthException(String resource,String message) {
        super();
        this.message = message;
        this.resource = resource;
    }
}

