package com.evcharging.station.Service;

import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.Booking;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;

import java.util.List;

public interface UserService {
    UserDTO getUserById(int userId);
    UserDTO createUser(UserDTO user);
    List<UserDTO> getAllUser();
    ResponseTemplate deleteUser(int Id);
    String CheckUserAndSave(UserDTO userDTO, HttpServletResponse http);
}
