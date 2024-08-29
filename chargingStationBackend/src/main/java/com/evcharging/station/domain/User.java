package com.evcharging.station.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false,length = 25,name = "FirstName")
    private String firstName;
    @Column(nullable = false,length = 25,name = "LastName")
    private String lastName;
    @Column(nullable = false,length = 50,unique = true,name = "Email")
    private  String emailId;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<FeedBack> feedbacks=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Booking> userBookings=new ArrayList<>();
}
