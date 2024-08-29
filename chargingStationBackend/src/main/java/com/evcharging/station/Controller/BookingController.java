package com.evcharging.station.Controller;


import com.evcharging.station.Config.TokenGenerator;
import com.evcharging.station.DTO.BookingDTO;
import com.evcharging.station.DTO.TimeSlotDTO;
import com.evcharging.station.RuntimeException.AuthException;
import com.evcharging.station.Templates.BookingResponse;
import com.evcharging.station.Templates.SlotAvailabilityRequest;
import com.evcharging.station.domain.Booking;
import com.evcharging.station.domain.TimeSlot;
import com.evcharging.station.Service.BookingService;
import com.evcharging.station.Templates.BookingRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TokenGenerator tokenGenerator;
    @PostMapping("/create")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingRequest bookingRequest, HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("user", "not logged in");
        }
        BookingDTO booking = bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(booking, HttpStatusCode.valueOf(201));
    }
    @GetMapping("/all/chargingslot/{chargingslotid}")
    public  ResponseEntity<List<BookingDTO>> getAllBookingsforSlot(@PathVariable int chargingslotid,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        List<BookingDTO> allChargingSlotBooking = bookingService.getAllChargingSlotBooking(chargingslotid);

        return new ResponseEntity<>(allChargingSlotBooking,HttpStatusCode.valueOf(200));
    }
    @GetMapping("all/chargingstation/{stationId}")
    public  ResponseEntity<List<BookingResponse>>getStationBookingTodays(@PathVariable int stationId ,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        List<BookingResponse> allChargingStationBooking = bookingService.getAllChargingStationBooking(stationId);
        return new ResponseEntity<>(allChargingStationBooking,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<BookingResponse>> getUserBookingTodays(@PathVariable int userid, HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("user", "not logged in");
        }
        List<BookingResponse> allUserBooking = bookingService.getAllUserBooking(userid);
        return new ResponseEntity<>(allUserBooking,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/cancle/{bookingId}")
    public ResponseEntity<String> cancleBooking(@PathVariable int bookingId,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("user", "not logged in");
        }
        String s = bookingService.cancleBooking(bookingId);
        return new ResponseEntity<>(s, HttpStatusCode.valueOf(200));
    }
    @GetMapping("/booked/{chargingslotid}")
    public  ResponseEntity<List<TimeSlotDTO>> getBookedTimeslot(@PathVariable int chargingslotid,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        List<TimeSlotDTO> availableTimeslot = bookingService.getBookedTimeslot(chargingslotid);
        return new ResponseEntity<>(availableTimeslot,HttpStatusCode.valueOf(200));
    }
    @PostMapping("/availability/chargingslot")
    public  ResponseEntity<List<TimeSlot>> getAvailableTimeslot(@RequestBody SlotAvailabilityRequest slotAvailabilityRequest){
        List<TimeSlot> availableTimeslot = bookingService.getAvailableTimeslot(slotAvailabilityRequest);
        return new ResponseEntity<>(availableTimeslot,HttpStatusCode.valueOf(200));
    }
}
