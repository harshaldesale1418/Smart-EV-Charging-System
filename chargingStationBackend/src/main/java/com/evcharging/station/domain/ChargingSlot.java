package com.evcharging.station.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChargingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotId;
    @Column(nullable = false,name = "RatePerHour")
    private double pricePerHour;
    @Column(nullable = false,name = "Availability")
    private boolean available;
    @ManyToOne
    @JoinColumn(name = "chargingStation")
    @JsonIgnore
    private  ChargingStation chargingStation;
}
