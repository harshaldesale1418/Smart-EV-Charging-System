package com.evcharging.station.Service;

import com.evcharging.station.DTO.FeedBackDTO;
import com.evcharging.station.Templates.ResponseTemplate;

import java.util.List;

public interface FeedbackService {
    FeedBackDTO addFeedbackOnStation(int chargingStationId,FeedBackDTO feedBackDTO,int userId);
    List<FeedBackDTO> getAllFeedbackOfStation(int chargingStationId);
    List<FeedBackDTO> getAllFeedbackOfUser(int userId);

    ResponseTemplate deleteFeedback(int id);
}
