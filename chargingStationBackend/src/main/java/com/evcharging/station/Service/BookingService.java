package com.evcharging.station.Service;

import com.evcharging.station.DTO.BookingDTO;
import com.evcharging.station.DTO.TimeSlotDTO;
import com.evcharging.station.Templates.BookingResponse;
import com.evcharging.station.Templates.SlotAvailabilityRequest;
import com.evcharging.station.domain.Booking;
import com.evcharging.station.domain.TimeSlot;
import com.evcharging.station.Templates.BookingRequest;

import java.util.Date;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingRequest bookingDetails);
    List<BookingResponse> getAllChargingStationBooking(int ChargingStationId);
    List<BookingDTO> getAllChargingSlotBooking(int ChargingSlotId);
    List<BookingDTO> getAllChargingSlotBookingByDate(int ChargingSlotId, Date date);

    List<BookingResponse> getAllUserBooking(int userId);
    List<BookingDTO> getAllUserBookingByDate(int userId,Date date);

    String cancleBooking(int bookingId);
    List<TimeSlotDTO> getBookedTimeslot(int chargingSlotId);
    List<TimeSlot> getAvailableTimeslot(SlotAvailabilityRequest slotAvailabilityRequest);


}
