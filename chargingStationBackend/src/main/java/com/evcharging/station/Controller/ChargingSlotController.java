package com.evcharging.station.Controller;

import com.evcharging.station.Config.TokenGenerator;
import com.evcharging.station.DTO.ChargingSlotDTO;
import com.evcharging.station.DTO.ChargingStationDTO;
import com.evcharging.station.RuntimeException.AuthException;
import com.evcharging.station.Service.ChargingSlotService;
import com.evcharging.station.Templates.ResponseTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/chargingslot")
public class ChargingSlotController {
    @Autowired
    private ChargingSlotService chargingSlotService;
    @Autowired
    private TokenGenerator tokenGenerator;

    @PostMapping("/addslot/{chargingStationId}")
    public ResponseEntity<ChargingStationDTO> createChargingSlot(@RequestBody ChargingSlotDTO chargingSlotDTO, @PathVariable int chargingStationId, HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        ChargingStationDTO chargingStationDTO = chargingSlotService.addChargingSlot(chargingSlotDTO,chargingStationId);
        return  new ResponseEntity<>(chargingStationDTO, HttpStatusCode.valueOf(201));

    }
    @PutMapping("/update")
    public ResponseEntity<ResponseTemplate> updateChargingSlot(@RequestBody ChargingSlotDTO chargingSlotDTO,HttpServletRequest http){
        boolean validToken = tokenGenerator.isValidToken(http);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        ResponseTemplate responseTemplate = chargingSlotService.updateChargingSlot(chargingSlotDTO);
        return new ResponseEntity<>(responseTemplate,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{chargingSlotId}")
    public ResponseEntity<ChargingSlotDTO> getSlotById(@PathVariable int chargingSlotId,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        ChargingSlotDTO chargingSlotById = chargingSlotService.getChargingSlotById(chargingSlotId);
        return  new ResponseEntity<>(chargingSlotById,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/all/{chargingStationId}")
    public  ResponseEntity<List<ChargingSlotDTO>> getAllSlotOfStation(@PathVariable int chargingStationId){
        List<ChargingSlotDTO> allChargingSlotByChargingId = chargingSlotService.getAllChargingSlotByChargingId(chargingStationId);
        return  new ResponseEntity<>(allChargingSlotByChargingId,HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/{Id}")
    public ResponseEntity<ResponseTemplate> deleteSlot(@PathVariable int Id,HttpServletRequest request){
        boolean validToken = tokenGenerator.isValidToken(request);
        if(!validToken) {
            throw new AuthException("station", "not logged in");
        }
        ResponseTemplate responseTemplate = chargingSlotService.deleteSlot(Id);
        return new ResponseEntity<>(responseTemplate,HttpStatusCode.valueOf(200));
    }

}
