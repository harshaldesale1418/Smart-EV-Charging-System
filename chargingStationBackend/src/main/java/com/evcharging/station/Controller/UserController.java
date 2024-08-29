package com.evcharging.station.Controller;

import com.evcharging.station.Config.TokenGenerator;
import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.RuntimeException.AuthException;
import com.evcharging.station.Service.UserService;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.Booking;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId, HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("user", "not logged in");
        }
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(201));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser(HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        List<UserDTO> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser, HttpStatusCode.valueOf(201));
    }

}
