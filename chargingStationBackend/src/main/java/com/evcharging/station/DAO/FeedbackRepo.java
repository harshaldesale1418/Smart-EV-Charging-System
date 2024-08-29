package com.evcharging.station.DAO;

import com.evcharging.station.domain.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<FeedBack,Integer> {

}
