package com.evcharging.station.Controller;

import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.Service.ChargingStationService;
import com.evcharging.station.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChargingStationService chargingstationService;

    @GetMapping("/user")
    public RedirectView getLoggedInUser(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response){
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email=oauth2User.getAttribute("email");
        String firstname=oauth2User.getAttribute("given_name");
        String lastname=oauth2User.getAttribute("family_name");
        UserDTO u=new UserDTO();
        u.setEmailId(email);
        u.setFirstName(firstname);
        u.setLastName(lastname);
        String token = userService.CheckUserAndSave(u,response);

        // Construct the redirect URL with user information as query parameters
        String encodedToken=URLEncoder.encode(token);
        String role=URLEncoder.encode("user");
        String redirectUrl = "https://charge-ev.projects.bbdgrad.com?token=" + encodedToken+"&role="+role;;

        // Redirect to the frontend with user information included
        return new RedirectView(redirectUrl);
    }

    @GetMapping("/chargingstation")
    public RedirectView getLoggedInChargingStation(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response){
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email=oauth2User.getAttribute("email");
        String token = chargingstationService.CheckStationAndSave(email,response);
        String encodedToken=URLEncoder.encode(token);
        String role=URLEncoder.encode("chargingstation");
        String redirectUrl = "https://charge-ev.projects.bbdgrad.com?token=" + encodedToken+"&role="+role;

        return new RedirectView(redirectUrl);
    }
}