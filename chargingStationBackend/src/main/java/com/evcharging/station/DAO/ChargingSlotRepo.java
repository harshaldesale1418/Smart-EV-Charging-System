package com.evcharging.station.DAO;

import com.evcharging.station.domain.ChargingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingSlotRepo extends JpaRepository<ChargingSlot,Integer> {

}