package com.evcharging.station.Service;

import com.evcharging.station.DTO.TimeSlotDTO;

import java.util.List;

public interface TimeslotService {
    List<TimeSlotDTO> createAllTimeSlot();
    List<TimeSlotDTO> getAllTimeSlot();
}
