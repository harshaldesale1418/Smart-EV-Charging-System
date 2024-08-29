package com.evcharging.station.Controller;

import com.evcharging.station.Config.TokenGenerator;
import com.evcharging.station.DTO.FeedBackDTO;
import com.evcharging.station.RuntimeException.AuthException;
import com.evcharging.station.Service.FeedbackService;
import com.evcharging.station.Templates.ResponseTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private TokenGenerator tokenGenerator;
    @PostMapping("/add/{stationId}/{userId}")
    public ResponseEntity<FeedBackDTO> addFeedbackOnStation(@RequestBody FeedBackDTO feedBackDTO, @PathVariable int stationId, @PathVariable int userId, HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("user", "not logged in");
        }
        FeedBackDTO feedback = feedbackService.addFeedbackOnStation(stationId, feedBackDTO, userId);

        return new ResponseEntity<>(feedback, HttpStatusCode.valueOf(201));



    }
    @GetMapping("/all/station/{stationId}")
    public  ResponseEntity<List<FeedBackDTO>> getAllFeedBackStation(@PathVariable int stationId){
        List<FeedBackDTO> allFeedbackOfStation = feedbackService.getAllFeedbackOfStation(stationId);
        return new ResponseEntity<>(allFeedbackOfStation,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/all/user/{userId}")
    public  ResponseEntity<List<FeedBackDTO>> getAllFeedBackUser(@PathVariable int userId,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        List<FeedBackDTO> allFeedbackOfStation = feedbackService.getAllFeedbackOfUser(userId);
        return new ResponseEntity<>(allFeedbackOfStation,HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> deleteFeedback(@PathVariable int id,HttpServletRequest request){
        ResponseTemplate responseTemplate = feedbackService.deleteFeedback(id);
        return  new ResponseEntity<>(responseTemplate,HttpStatusCode.valueOf(200));
    }
}
