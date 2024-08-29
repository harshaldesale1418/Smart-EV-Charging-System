package com.evcharging.station.Controller;

import com.evcharging.station.DTO.TimeSlotDTO;
import com.evcharging.station.Service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeslot")
public class TimeSlotController {
    @Autowired
    private TimeslotService timeslotService;

//    @GetMapping ("/createall")
//    public ResponseEntity<List<TimeSlotDTO>> createAndGetAllSlot(){
//        List<TimeSlotDTO> allTimeSlot = timeslotService.createAllTimeSlot();
//        return  new ResponseEntity<>(allTimeSlot, HttpStatusCode.valueOf(200));
//    }




}
