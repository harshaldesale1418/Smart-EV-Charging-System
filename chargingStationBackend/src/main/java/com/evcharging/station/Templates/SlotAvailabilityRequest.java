package com.evcharging.station.Templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotAvailabilityRequest {
    private Date date;
    private int chargingSlotId;
    private int stationId;
}
