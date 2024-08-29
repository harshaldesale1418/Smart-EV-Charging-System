package com.evcharging.station.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStationDTO {
    private int stationId;
    private String name;
    private double longitude;
    private double latitude;
    private String emailId;
    private int openTime;
    private int closeTime;
    private List<ChargingSlotDTO> chargingSlotDTOS;
}
