package com.evcharging.station.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "TimeSlotId")
    private int timeSlotId;
    @Column(nullable = false,name = "StartTime")
    private int startTime;
    @Column(nullable = false,name = "EndTime")
    private int endTime;

}
