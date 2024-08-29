package com.evcharging.station.Service.impl;

import com.evcharging.station.DAO.TimeslotRepo;
import com.evcharging.station.DTO.TimeSlotDTO;
import com.evcharging.station.domain.TimeSlot;
import com.evcharging.station.Service.TimeslotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TimeslotServiceImpl implements TimeslotService {
    @Autowired
    private TimeslotRepo timeslotRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TimeSlotDTO> createAllTimeSlot() {
        timeslotRepo.deleteAll();
        List<TimeSlotDTO>ls=new ArrayList<>();

        for(int i=0;i<24;i++){


            TimeSlot t=new TimeSlot();
            t.setStartTime(i);
            t.setEndTime(i+1);
            TimeSlot saved = timeslotRepo.save(t);
            ls.add(modelMapper.map(saved,TimeSlotDTO.class));
        }
        return ls;
    }



    @Override
    public List<TimeSlotDTO> getAllTimeSlot() {

        List<TimeSlot> all = timeslotRepo.findAll();
        List<TimeSlotDTO> ls=new ArrayList<>();
        for (TimeSlot t:all){
            ls.add(modelMapper.map(t, TimeSlotDTO.class));
        }
        return  ls;
    }


}
