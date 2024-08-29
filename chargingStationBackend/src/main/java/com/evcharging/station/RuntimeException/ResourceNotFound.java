package com.evcharging.station.RuntimeException;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class ResourceNotFound extends RuntimeException{
    private String resource;
    private String message;
    public ResourceNotFound(String resource,String message) {
        super();
        this.resource = resource;
        this.message = message;
    }

}
