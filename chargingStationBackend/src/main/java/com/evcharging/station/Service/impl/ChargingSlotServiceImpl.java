package com.evcharging.station.Service.impl;

import com.evcharging.station.DAO.ChargingSlotRepo;
import com.evcharging.station.DAO.ChargingStationRepo;
import com.evcharging.station.DTO.ChargingSlotDTO;
import com.evcharging.station.DTO.ChargingStationDTO;
import com.evcharging.station.RuntimeException.ResourceAlreadyExist;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.ChargingSlot;
import com.evcharging.station.domain.ChargingStation;
import com.evcharging.station.RuntimeException.ResourceNotFound;
import com.evcharging.station.Service.ChargingSlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChargingSlotServiceImpl implements ChargingSlotService {

    @Autowired
    private ChargingSlotRepo chargingSlotRepo;
    @Autowired
    private ChargingStationRepo chargingStationRepo;
    @Autowired
    private ModelMapper modelMapper;




    @Override
    public ChargingStationDTO addChargingSlot(ChargingSlotDTO chargingSlotDTO, int chargingStationId) {
        Optional<ChargingStation> isChargingStation = chargingStationRepo.findById(chargingStationId);
        if(isChargingStation.isEmpty()){
           // System.out.println("can't add slot due to station is not exits");
            throw new ResourceNotFound("station","is not exist, create station first");
        }
        ChargingStation chargingStation = isChargingStation.get();
        if(chargingStation.getChargingSlots().size()<=4){
            List<ChargingSlot> slots=chargingStation.getChargingSlots();
            ChargingSlot slot = modelMapper.map(chargingSlotDTO, ChargingSlot.class);
            slot.setChargingStation(chargingStation);
            slots.add(slot);
            chargingStation.setChargingSlots(slots);
            ChargingStation savedChargingStation = chargingStationRepo.save(chargingStation);
            List<ChargingSlot> chargingSlots = savedChargingStation.getChargingSlots();
            List<ChargingSlotDTO> dtos=new ArrayList<>();
            for(ChargingSlot s:chargingSlots){
                dtos.add(modelMapper.map(s,ChargingSlotDTO.class));
            }


            ChargingStationDTO mappedDTOs = modelMapper.map(savedChargingStation, ChargingStationDTO.class);
            mappedDTOs.setChargingSlotDTOS(dtos);
            return  mappedDTOs;
        }else{
            throw new ResourceAlreadyExist("chargingslots","you can't add more than 4 slots");
        }
    }





    @Override
    public ChargingSlotDTO changeAvailablity(int chargingSlotId) {
        return null;
    }

    @Override
    public ChargingSlotDTO getChargingSlotById(int chargingSlotId) {
        Optional<ChargingSlot> slotbyId = chargingSlotRepo.findById(chargingSlotId);
        if(slotbyId.isEmpty()){
            System.out.println("charging slot is not exits");
            throw new ResourceNotFound("slot","is not exist,try again");

        }
        ChargingSlot chargingSlot = slotbyId.get();
        ChargingStation chargingStation = chargingSlot.getChargingStation();

        ChargingSlotDTO map = modelMapper.map(chargingSlot, ChargingSlotDTO.class);
        map.setChargingStationDTO(modelMapper.map(chargingStation,ChargingStationDTO.class));

        return map;
    }

    @Override
    public List<ChargingSlotDTO> getAllChargingSlotByChargingId(int chargingStationId) {
        Optional<ChargingStation> byId = chargingStationRepo.findById(chargingStationId);
        if(byId.isEmpty()){
            System.out.println("station is not present");
            throw new ResourceNotFound("station","is not presesnt, try again");
        }

        ChargingStation chargingStation = byId.get();

        List<ChargingSlotDTO> chargingSlotDTOS=new ArrayList<>();
        List<ChargingSlot> chargingSlots = chargingStation.getChargingSlots();

        for (ChargingSlot chargingSlot : chargingSlots) {
            chargingSlotDTOS.add(modelMapper.map(chargingSlot,ChargingSlotDTO.class));
        }

        return chargingSlotDTOS;
    }
    @Override
    public ResponseTemplate deleteSlot(int id){
        Optional<ChargingSlot> byId = chargingSlotRepo.findById(id);
        if(byId.isEmpty()){
            throw  new ResourceNotFound("slot ","not found");
        }
        chargingSlotRepo.delete(byId.get());
        return  new ResponseTemplate("slot deleted successfully",true);
    }

    @Override
    public ResponseTemplate updateChargingSlot(ChargingSlotDTO chargingSlotDTO) {
        Optional<ChargingSlot> byId = chargingSlotRepo.findById(chargingSlotDTO.getSlotId());
        if(byId.isEmpty()){
            throw  new ResourceNotFound("slot ","not found");
        }
        ChargingSlot chargingSlot = byId.get();
        chargingSlot.setAvailable(chargingSlotDTO.isAvailable());
        chargingSlot.setPricePerHour(chargingSlotDTO.getPricePerHour());
        chargingSlotRepo.save(chargingSlot);
        return new ResponseTemplate("chargingslot updated",true);
    }


}
