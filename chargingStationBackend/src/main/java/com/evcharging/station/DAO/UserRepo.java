package com.evcharging.station.DAO;

import com.evcharging.station.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmailId(String email);
}
