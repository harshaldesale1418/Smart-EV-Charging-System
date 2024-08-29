package com.evcharging.station.DAO;

import com.evcharging.station.domain.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingStationRepo extends JpaRepository<ChargingStation,Integer> {
    ChargingStation findByEmailId(String emailId);
}
