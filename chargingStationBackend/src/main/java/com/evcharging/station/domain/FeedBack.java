package com.evcharging.station.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;
    @Column(nullable = false,length =150,name = "Description")
    private String feedback;
    @Column(nullable = false,name = "Rating")
    private int rating;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private  User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chargingStationId")
    private ChargingStation chargingStation;

}