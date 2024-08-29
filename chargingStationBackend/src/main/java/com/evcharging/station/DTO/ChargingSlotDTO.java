package com.evcharging.station.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargingSlotDTO {

    private int slotId;
    private double pricePerHour;
    private boolean available;
    private ChargingStationDTO chargingStationDTO;

}
