package com.evcharging.station.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackDTO {
    private int feedbackId;
    private String feedback;
    private int rating;
}
