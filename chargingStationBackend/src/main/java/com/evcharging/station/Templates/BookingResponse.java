package com.evcharging.station.Templates;

import com.evcharging.station.DTO.ChargingSlotDTO;
import com.evcharging.station.DTO.TimeSlotDTO;
import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.domain.ChargingSlot;
import com.evcharging.station.domain.ChargingStation;
import com.evcharging.station.domain.TimeSlot;
import com.evcharging.station.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private int bookingId;


    private Date date;
    private User user;
    private ChargingSlot chargingSlot;
    private TimeSlot timeSlot;
    private String status;
    private ChargingStation chargingStation;
}
