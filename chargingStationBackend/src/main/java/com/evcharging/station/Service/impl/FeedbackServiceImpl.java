package com.evcharging.station.Service.impl;

import com.evcharging.station.DAO.ChargingStationRepo;
import com.evcharging.station.DAO.FeedbackRepo;
import com.evcharging.station.DAO.UserRepo;
import com.evcharging.station.DTO.FeedBackDTO;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.ChargingStation;
import com.evcharging.station.domain.FeedBack;
import com.evcharging.station.domain.User;
import com.evcharging.station.RuntimeException.ResourceNotFound;
import com.evcharging.station.Service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChargingStationRepo chargingStationRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public FeedBackDTO addFeedbackOnStation(int chargingStationId, FeedBackDTO feedBackDTO, int userId) {
        FeedBack feedback = modelMapper.map(feedBackDTO, FeedBack.class);
        Optional<User> isUser = userRepo.findById(userId);

        Optional<ChargingStation> isStation = chargingStationRepo.findById(chargingStationId);
        if(isStation.isEmpty() || isUser.isEmpty()){
            System.out.println("something went wrong");
            return null;
        }
        feedback.setUser(isUser.get());
        feedback.setChargingStation(isStation.get());
        FeedBack saved = feedbackRepo.save(feedback);

        return modelMapper.map(saved,FeedBackDTO.class);
    }

    @Override
    public List<FeedBackDTO> getAllFeedbackOfStation(int chargingStationId) {
        Optional<ChargingStation> isStation = chargingStationRepo.findById(chargingStationId);
        if(isStation.isEmpty()){
            System.out.println("station is not available");
            throw new ResourceNotFound("station","is not available");
        }
        ChargingStation chargingStation = isStation.get();
        List<FeedBack> feedbacks = chargingStation.getFeedbacks();

        List<FeedBackDTO> ls=new ArrayList<>();
        for (FeedBack f : feedbacks){
            ls.add(modelMapper.map(f,FeedBackDTO.class));

        }
        return ls;
    }

    @Override
    public List<FeedBackDTO> getAllFeedbackOfUser(int userId) {
        Optional<User> isUser = userRepo.findById(userId);
        if(isUser.isEmpty()){
            System.out.println("user is not available");
            throw new ResourceNotFound("user","not available");
        }
        User user = isUser.get();
        List<FeedBack> feedbacks = user.getFeedbacks();
        List<FeedBackDTO> feedBackDTOS=new ArrayList<>();
        for (FeedBack f:feedbacks){
            feedBackDTOS.add(modelMapper.map(f,FeedBackDTO.class));
        }
        return feedBackDTOS;
    }

    @Override
    public ResponseTemplate deleteFeedback(int id) {
        Optional<FeedBack> byId = feedbackRepo.findById(id);
        if(byId.isEmpty()){
            throw  new ResourceNotFound("feedback ","not found");
        }
        feedbackRepo.delete(byId.get());

        return new ResponseTemplate("feedback deleted successfully",false);
    }
}
