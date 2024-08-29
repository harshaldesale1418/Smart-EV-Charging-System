package com.evcharging.station.Service.impl;

import com.evcharging.station.Config.TokenGenerator;
import com.evcharging.station.DAO.UserRepo;
import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.Templates.ResponseTemplate;
import com.evcharging.station.domain.Booking;
import com.evcharging.station.domain.User;
import com.evcharging.station.RuntimeException.ResourceAlreadyExist;
import com.evcharging.station.RuntimeException.ResourceNotFound;
import com.evcharging.station.Service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Override
    public UserDTO getUserById(int userId) {
        Optional<User> isUser = userRepo.findById(userId);
        if(isUser.isEmpty()){
            System.out.println("user is not available");
            throw new ResourceNotFound("user","not available");
        }
        User user = isUser.get();
         return  modelMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User byEmailId = userRepo.findByEmailId(userDTO.getEmailId());
        if(byEmailId!=null){
            System.out.println("user already exits");
            throw new ResourceAlreadyExist("user","already Exist");
        }
        User mappedUser = modelMapper.map(userDTO, User.class);
        User savedUser = userRepo.save(mappedUser);
        return modelMapper.map(savedUser,UserDTO.class);

    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> allUser = userRepo.findAll();
        List<UserDTO>  allUserDTOs=new ArrayList<>();
        for(User u:allUser){
            allUserDTOs.add(modelMapper.map(u,UserDTO.class));
        }
        return allUserDTOs;
    }

    @Override
    public ResponseTemplate deleteUser(int Id) {
        Optional<User> u= userRepo.findById(Id);
        if(u.isEmpty()){
            throw new ResourceNotFound("user","not found");
        }
        userRepo.deleteById(Id);
        return  new ResponseTemplate("User deleted successfully",true);
    }

    @Override
    public String CheckUserAndSave(UserDTO userDTO,HttpServletResponse http) {
        User byEmailId = userRepo.findByEmailId(userDTO.getEmailId());
        if(byEmailId!=null){
            System.out.println("user already exits");
            String token = tokenGenerator.generateToken(byEmailId.getEmailId(),byEmailId.getUserId());
            System.out.println(token);

            Cookie c=new Cookie("web-vb-token",token);
            c.setMaxAge(5 * 60 * 60);
            http.addCookie(c);
            return  token;
        }
        User mappedUser = modelMapper.map(userDTO, User.class);
        User save = userRepo.save(mappedUser);
        String token = tokenGenerator.generateToken(save.getEmailId(),save.getUserId());
        Cookie c=new Cookie("web-vb-token",token);
        c.setMaxAge(5 * 60 * 60);
        http.addCookie(c);
        System.out.println(token);
        return token;
    }
}