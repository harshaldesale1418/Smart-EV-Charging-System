package com.evcharging.station.Service;

import com.evcharging.station.DTO.ChargingStationDTO;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.ChargingStation;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ChargingStationService {
    ChargingStationDTO getChargingStationById(int chargingStationId);
    List<ChargingStation> getAllChargingStation();
    ChargingStationDTO updateChargingStation(ChargingStationDTO chargingStationDTO);

    ResponseTemplate deleteChargingStation(int id);
    String CheckStationAndSave(String email, HttpServletResponse response);
}
