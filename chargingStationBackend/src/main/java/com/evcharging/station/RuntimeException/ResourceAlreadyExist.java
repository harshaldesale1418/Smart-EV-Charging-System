package com.evcharging.station.RuntimeException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExist extends RuntimeException{
    private String message;
    private String resource;
    public ResourceAlreadyExist(String resource,String message) {
        super();
        this.message = message;
        this.resource = resource;
    }
}

