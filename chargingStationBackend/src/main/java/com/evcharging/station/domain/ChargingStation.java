package com.evcharging.station.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stationId;
    @Column(length = 50, name = "StationName")
    private String name;
    @Column(name = "Longitude")
    private double longitude;
    @Column(name = "Latitude")
    private double latitude;
    @Column(nullable = false, length = 40, name = "EmailId", unique = true)
    private String emailId;
    @Column(name = "OpenTime")
    private int openTime;
    @Column(name = "CloseTime")
    private int closeTime;
    @OneToMany(mappedBy = "chargingStation", cascade = CascadeType.ALL)
    private List<ChargingSlot> chargingSlots = new ArrayList<>();
    //    @OneToMany(mappedBy = "chargingStation" ,cascade = CascadeType.ALL)
//    private List<Booking> stationBookigs=new ArrayList<>();
    @OneToMany(mappedBy = "chargingStation", cascade = CascadeType.ALL)
    private List<FeedBack> feedbacks = new ArrayList<>();
}
