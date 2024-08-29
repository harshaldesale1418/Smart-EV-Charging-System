package com.evcharging.station.DAO;

import com.evcharging.station.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Integer> {

    Booking findByDateAndChargingSlotAndTimeSlotIdAndStatus(Date date, ChargingSlot chargingSlot, int timeSlotId,String status);
    List<Booking> findAllByUserAndDate(User user,Date date);
    List<Booking> findAllByUser(User user);
    List<Booking> findAllByChargingSlotAndDate(ChargingSlot chargingSlot, Date date);
    List<Booking> findAllByChargingSlot(ChargingSlot chargingSlot);
    List<Booking> findAllByChargingSlotAndDateAndStatus(ChargingSlot chargingSlot,Date date,String status);

}
