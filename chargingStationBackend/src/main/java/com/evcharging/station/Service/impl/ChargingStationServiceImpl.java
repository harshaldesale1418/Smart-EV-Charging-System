package com.evcharging.station.Service.impl;

import com.evcharging.station.Config.TokenGenerator;
import com.evcharging.station.DAO.ChargingStationRepo;
import com.evcharging.station.DTO.ChargingStationDTO;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.ChargingStation;
import com.evcharging.station.RuntimeException.ResourceAlreadyExist;
import com.evcharging.station.RuntimeException.ResourceNotFound;
import com.evcharging.station.Service.ChargingStationService;

import com.evcharging.station.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class ChargingStationServiceImpl implements ChargingStationService {

    @Autowired
    private ChargingStationRepo chargingStationRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Override
    public ChargingStationDTO getChargingStationById(int chargingStationId) {
        Optional<ChargingStation> isChargingStation = chargingStationRepo.findById(chargingStationId);
        if(isChargingStation.isEmpty()){
            System.out.println("station is not available");
            throw new ResourceNotFound("Station","not found, try again");
        }
       return modelMapper.map(isChargingStation.get(),ChargingStationDTO.class);
    }

    @Override
    public List<ChargingStation> getAllChargingStation() {
        List<ChargingStation> allChargingStation = chargingStationRepo.findAll();
//        List<ChargingStationDTO> allChargingStationDTOs=new ArrayList<>();
//        for (ChargingStation c:allChargingStation){
//            allChargingStationDTOs.add(modelMapper.map(c,ChargingStationDTO.class));
//
//        }
        return  allChargingStation;


    }

    @Override
    public ChargingStationDTO updateChargingStation(ChargingStationDTO chargingStationDTO) {

        ChargingStation isExits = chargingStationRepo.findByEmailId(chargingStationDTO.getEmailId());
        if(isExits==null){
            throw  new ResourceNotFound("charging station","not found");
        }
        isExits.setName(chargingStationDTO.getName());
        isExits.setLatitude(chargingStationDTO.getLatitude());
        isExits.setLongitude(chargingStationDTO.getLongitude());
        isExits.setOpenTime(chargingStationDTO.getOpenTime());
        isExits.setCloseTime(chargingStationDTO.getCloseTime());
        ChargingStation save = chargingStationRepo.save(isExits);
        return modelMapper.map(save,ChargingStationDTO.class);
    }

    @Override
    public ResponseTemplate deleteChargingStation(int id) {
        Optional<ChargingStation>byid=chargingStationRepo.findById(id);
        if(byid.isEmpty()){
            throw  new ResourceNotFound("charging station","not found");
        }
        chargingStationRepo.delete(byid.get());
        return new ResponseTemplate("charging slot deleted",true);
    }

    @Override
    public String CheckStationAndSave(String email, HttpServletResponse http) {
        ChargingStation byEmailId= chargingStationRepo.findByEmailId(email);
        if(byEmailId!=null){
            System.out.println("user already exits");
            String token = tokenGenerator.generateToken(byEmailId.getEmailId(),byEmailId.getStationId());
            System.out.println(token);

            Cookie c=new Cookie("web-station-token",token);
            c.setMaxAge(5 * 60 * 60);
            http.addCookie(c);
            return  token;
        }
        ChargingStation newchargepoint=new ChargingStation();
        newchargepoint.setEmailId(email);
        ChargingStation save = chargingStationRepo.save(newchargepoint);
        String token = tokenGenerator.generateToken(save.getEmailId(),save.getStationId());
        Cookie c=new Cookie("web-station-token",token);
        c.setMaxAge(5 * 60 * 60);
        http.addCookie(c);
        System.out.println(token);
        return token;
    }

}
