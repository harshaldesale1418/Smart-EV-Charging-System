package com.evcharging.station.Service.impl;

import com.evcharging.station.DAO.*;
import com.evcharging.station.DTO.BookingDTO;
import com.evcharging.station.DTO.ChargingSlotDTO;
import com.evcharging.station.DTO.TimeSlotDTO;
import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.Templates.BookingResponse;
import com.evcharging.station.Templates.SlotAvailabilityRequest;
import com.evcharging.station.domain.*;
import com.evcharging.station.RuntimeException.ResourceAlreadyExist;
import com.evcharging.station.RuntimeException.ResourceNotFound;
import com.evcharging.station.Service.BookingService;
import com.evcharging.station.Templates.BookingRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChargingSlotRepo chargingSlotRepo;

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private TimeslotRepo timeslotRepo;
    @Autowired
    private ChargingStationRepo chargingStationRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookingDTO createBooking(BookingRequest bookingRequest) {

        Optional<TimeSlot> isTime = timeslotRepo.findById(bookingRequest.getTimeSlotId());
        Optional<ChargingSlot>isSlot = chargingSlotRepo.findById(bookingRequest.getChargingSlotId());
        Optional<User> isUser = userRepo.findById(bookingRequest.getUserId());

        if(isTime.isEmpty()||isUser.isEmpty()||isSlot.isEmpty()){
            throw new ResourceNotFound(" ","All Fields are required ,fill all fields");
        }
        Booking exitingBooking = bookingRepo.findByDateAndChargingSlotAndTimeSlotIdAndStatus(bookingRequest.getDate(), isSlot.get(), bookingRequest.getTimeSlotId(),"confirmed");
        if(exitingBooking!=null ) {
            throw new ResourceAlreadyExist("slot", "is already Occupied");
        }
        int openTime = isSlot.get().getChargingStation().getOpenTime();
        int closeTime = isSlot.get().getChargingStation().getCloseTime();
        int timeSlotId = isTime.get().getTimeSlotId();

        if(openTime<timeSlotId && timeSlotId<=closeTime) {
            Booking b = new Booking();
            b.setUser(isUser.get());
            b.setChargingSlot(isSlot.get());
            b.setTimeSlotId(bookingRequest.getTimeSlotId());
            b.setDate(bookingRequest.getDate());
            b.setStatus("confirmed");
            Booking save = bookingRepo.save(b);
            BookingDTO bdto = new BookingDTO();
            bdto.setBookingId(save.getBookingId());
            bdto.setUser(modelMapper.map(save.getUser(), UserDTO.class));
            bdto.setChargingSlot(modelMapper.map(save.getChargingSlot(), ChargingSlotDTO.class));
            bdto.setDate(save.getDate());
            bdto.setTimeSlot(modelMapper.map(isTime.get(), TimeSlotDTO.class));
            bdto.setStatus(save.getStatus());
            return bdto;
        }
        else{
            throw new ResourceNotFound("TimeSlot","is not in the working hours");
        }
    }

    @Override
    public List<BookingResponse> getAllChargingStationBooking(int ChargingStationId) {
        Optional<ChargingStation> byId = chargingStationRepo.findById(ChargingStationId);
        if(byId.isEmpty()){
            throw new ResourceNotFound("station", "is not found");
        }
        List<ChargingSlot> chargingSlots = byId.get().getChargingSlots();
        List<Booking> allBookings=new ArrayList<>();

        List<BookingResponse> bookingResponses=new ArrayList<>();
        for (ChargingSlot b: chargingSlots){

            List<Booking> allByChargingSlot = bookingRepo.findAllByChargingSlot(b);
            for(Booking a: allByChargingSlot){
                BookingResponse bt=new BookingResponse();
                User user = a.getUser();
                user.setUserBookings(null);
                user.setFeedbacks(null);
                bt.setUser(user);
                bt.setBookingId(a.getBookingId());
                bt.setDate(a.getDate());
                Optional<TimeSlot> byId1 = timeslotRepo.findById(a.getTimeSlotId());
                if(byId1.isEmpty()){
                    bt.setTimeSlot(null);
                }else {
                    bt.setTimeSlot(byId1.get());
                }
                ChargingSlot chargingSlot = a.getChargingSlot();
                bt.setChargingSlot(chargingSlot);
                bt.setChargingStation(null);
                bt.setStatus(a.getStatus());
                bookingResponses.add(bt);
                bookingResponses.add(bt);
            }
        }
        return bookingResponses;
    }

    @Override
    public List<BookingDTO> getAllChargingSlotBooking(int ChargingSlotId) {

        Optional<ChargingSlot> isSlot = chargingSlotRepo.findById(ChargingSlotId);
        if(isSlot.isEmpty()) {
            throw new ResourceNotFound("slot"," is not Available");
        }

        List<Booking> allByChargingSlotAndDate = bookingRepo.findAllByChargingSlot(isSlot.get());
        List<BookingDTO> bookingDTOS=new ArrayList<>();
        for (Booking b: allByChargingSlotAndDate){
            bookingDTOS.add(modelMapper.map(b, BookingDTO.class));

        }
        return bookingDTOS;
    }

    @Override
    public List<BookingDTO> getAllChargingSlotBookingByDate(int ChargingSlotId, Date date) {

        Optional<ChargingSlot> isSlot = chargingSlotRepo.findById(ChargingSlotId);
        if (isSlot.isEmpty()){
            throw new ResourceNotFound("slot", "is not found");
        }
        List<Booking> allByChargingSlotAndDate = bookingRepo.findAllByChargingSlotAndDate(isSlot.get(), date);
        List<BookingDTO> ls=new ArrayList<>();
        for (Booking b:allByChargingSlotAndDate){
            ls.add(modelMapper.map(b,BookingDTO.class));
        }
        return ls;
    }



    @Override
    public List<BookingResponse> getAllUserBooking(int userId) {
        Optional<User> isUser = userRepo.findById(userId);
        if(isUser.isEmpty()){
            throw new ResourceNotFound("user","not found, try again");
        }
        List<Booking> allByUser = bookingRepo.findAllByUser(isUser.get());
        List<BookingResponse> bookingResponses=new ArrayList<>();
        for (Booking b: allByUser){
            BookingResponse bt=new BookingResponse();
            User user = b.getUser();
            user.setUserBookings(null);
            user.setFeedbacks(null);
            bt.setUser(user);
            bt.setBookingId(b.getBookingId());
            bt.setDate(b.getDate());
            Optional<TimeSlot> byId1 = timeslotRepo.findById(b.getTimeSlotId());
            if(byId1.isEmpty()){
                bt.setTimeSlot(null);
            }else {
                bt.setTimeSlot(byId1.get());
            }
            ChargingSlot chargingSlot = b.getChargingSlot();
            bt.setChargingSlot(chargingSlot);
            ChargingStation chargingStation = chargingSlot.getChargingStation();
            chargingStation.setChargingSlots(null);
            chargingStation.setFeedbacks(null);
            bt.setChargingStation(chargingStation);
            bt.setStatus(b.getStatus());
            bookingResponses.add(bt);
        }
        return bookingResponses;
    }

    @Override
    public List<BookingDTO> getAllUserBookingByDate(int userId, Date date) {
        Optional<User> isUser = userRepo.findById(userId);
        if(isUser.isEmpty()){
            throw new ResourceNotFound("user","not found, try again");
        }
        List<Booking> allByUserAndDate = bookingRepo.findAllByUserAndDate(isUser.get(), date);
        List<BookingDTO> ls=new ArrayList<>();
        for (Booking b: allByUserAndDate){
            ls.add(modelMapper.map(b,BookingDTO.class));
        }
        return ls;
    }

    @Override
    public String cancleBooking(int bookingId) {
        Optional<Booking> isBooking = bookingRepo.findById(bookingId);
        if(isBooking.isEmpty()){
            throw new ResourceNotFound("booking","not available");
        }
        Booking booking = isBooking.get();
        Date todayDate=new Date();
        int timeSlotId = booking.getTimeSlotId();

        Optional<TimeSlot> isTime = timeslotRepo.findById(timeSlotId);
        if(isTime.isEmpty()){
            throw new ResourceNotFound("timeSlot"," not Available");
        }

        if(booking.getDate().compareTo(todayDate)>0){
            booking.setStatus("cancelled");
             bookingRepo.save(booking);
            System.out.println("cancelled future booking");
            return booking.getStatus();

        } else if (booking.getDate().compareTo(todayDate)==0) {
//            TimeSlot timeSlot = isTime.get();

            if(todayDate.getHours()<isTime.get().getStartTime()){
                booking.setStatus("cancelled");
                bookingRepo.save(booking);
                System.out.println("cancelled todays booking");
                return  booking.toString();
            }

        }
        return "you can't cancel the booking";

    }

    @Override
    public List<TimeSlotDTO> getBookedTimeslot(int chargingSlotId) {
        Optional<ChargingSlot> isSlot = chargingSlotRepo.findById(chargingSlotId);
        if(isSlot.isEmpty()){
            System.out.println("slot is not present");
            return  null;
        }
        List<Booking> allByChargingSlot = bookingRepo.findAllByChargingSlot(isSlot.get());
        List<TimeSlot> alltimeslot = timeslotRepo.findAll();


        List<TimeSlotDTO>ls =new ArrayList<>();
        for (Booking b:allByChargingSlot){
            Optional<TimeSlot> isTime = timeslotRepo.findById(b.getTimeSlotId());

            if(alltimeslot.contains(isTime.get())){
                ls.add(modelMapper.map(isTime.get(),TimeSlotDTO.class));
            }
        }
        return ls;

    }
    @Override
    public List<TimeSlot> getAvailableTimeslot(SlotAvailabilityRequest slotAvailabilityRequest) {
        Optional<ChargingSlot> isSlot = chargingSlotRepo.findById(slotAvailabilityRequest.getChargingSlotId());
        if (isSlot.isEmpty()) {
            System.out.println("Slot is not present");
            throw new ResourceNotFound("Slot", "not Present");
        }

        Optional<ChargingStation> isStation = chargingStationRepo.findById(slotAvailabilityRequest.getStationId());
        if (isStation.isEmpty()) {
            System.out.println("Charging station is not present");
            throw new ResourceNotFound("chargingstation", "not Present");
        }

        ChargingStation chargingStation = isStation.get();
        int openTime = chargingStation.getOpenTime(); // Assuming open time is in integer format (e.g., 18 for 6:00 PM)
        int closeTime = chargingStation.getCloseTime(); // Assuming close time is in integer format (e.g., 22 for 10:00 PM)

        List<TimeSlot> allTimeSlots = timeslotRepo.findAll();

        List<TimeSlot> availableTimeSlots = allTimeSlots.stream()
                .filter(timeSlot -> timeSlot.getStartTime() >= openTime && timeSlot.getEndTime() <= closeTime)
                .collect(Collectors.toList());

        List<Booking> allByChargingSlot = bookingRepo.findAllByChargingSlotAndDateAndStatus(isSlot.get(), slotAvailabilityRequest.getDate(), "confirmed");
        Set<Integer> bookedTimeSlotIds = allByChargingSlot.stream()
                .map(Booking::getTimeSlotId)
                .collect(Collectors.toSet());

        availableTimeSlots.removeIf(timeSlot -> bookedTimeSlotIds.contains(timeSlot.getTimeSlotId()));

        return availableTimeSlots;
    }
}
