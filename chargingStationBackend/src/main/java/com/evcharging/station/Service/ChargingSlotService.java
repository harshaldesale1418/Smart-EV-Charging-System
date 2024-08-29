package com.evcharging.station.Service;

import com.evcharging.station.DTO.ChargingSlotDTO;
import com.evcharging.station.DTO.ChargingStationDTO;
import com.evcharging.station.Templates.ResponseTemplate;

import java.util.List;

public interface ChargingSlotService {
    ChargingStationDTO addChargingSlot(ChargingSlotDTO chargingSlotDTO,int chargingStationId);



    ChargingSlotDTO changeAvailablity(int chargingSlotId);
    ChargingSlotDTO getChargingSlotById(int chargingSlotId);
    List<ChargingSlotDTO> getAllChargingSlotByChargingId(int chargingStationId);
    ResponseTemplate deleteSlot(int id);
    ResponseTemplate updateChargingSlot(ChargingSlotDTO chargingSlotDTO);
}
