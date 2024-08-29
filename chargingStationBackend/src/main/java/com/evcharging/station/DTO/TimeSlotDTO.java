package com.evcharging.station.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDTO {
    private int timeSlotId;
    private int startTime;
    private int endTime;

}
