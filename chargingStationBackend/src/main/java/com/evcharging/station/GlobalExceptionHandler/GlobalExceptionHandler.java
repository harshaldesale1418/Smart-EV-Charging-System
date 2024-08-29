package com.evcharging.station.GlobalExceptionHandler;

import com.evcharging.station.RuntimeException.AuthException;
import com.evcharging.station.RuntimeException.ResourceAlreadyExist;
import com.evcharging.station.RuntimeException.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExist.class)
    public ResponseEntity<Map<String,String>> alreadyExist(ResourceAlreadyExist ex) {
        String resource = ex.getResource();
        String message = ex.getMessage();
        Map<String, String> mp = new HashMap<>();
        mp.put("Error", resource + " " + message);
        return new ResponseEntity<Map<String, String>>(mp, HttpStatus.CONFLICT);//harshal
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp=new HashMap<String,String>();
        ex.getBindingResult().getAllErrors().forEach(e->{
            String field=((FieldError)e).getField();
            String defaultMessage=e.getDefaultMessage();
            resp.put(field,defaultMessage);
        });

        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String,String>> resourceNotFound(ResourceNotFound ex){
       String resource=ex.getResource();
       String msg=ex.getMessage();
       Map<String,String> mp=new HashMap<String,String>();
       mp.put("Error",resource+" "+msg);
       return new ResponseEntity<>(mp,HttpStatus.NOT_FOUND);//harshal

    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String,String>> alreadyExist(AuthException ex) {
        String resource = ex.getResource();
        String message = ex.getMessage();
        Map<String, String> mp = new HashMap<>();
        mp.put("Error", resource + " " + message);
        return new ResponseEntity<Map<String, String>>(mp, HttpStatus.CONFLICT);//harshal
    }


}
