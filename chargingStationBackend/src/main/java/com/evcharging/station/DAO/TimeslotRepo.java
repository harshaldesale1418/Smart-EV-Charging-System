package com.evcharging.station.DAO;

import com.evcharging.station.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepo extends JpaRepository<TimeSlot,Integer> {
}
