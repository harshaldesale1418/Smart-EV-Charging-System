package com.evcharging.station;

import com.evcharging.station.DAO.UserRepo;
import com.evcharging.station.DTO.UserDTO;
import com.evcharging.station.RuntimeException.ResourceAlreadyExist;
import com.evcharging.station.Service.impl.UserServiceImpl;
import com.evcharging.station.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepoImpl {
    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser_Success() {
        // Mock data
        UserDTO userDTO = new UserDTO();
        userDTO.setEmailId("test@example.com");

        User mappedUser = new User();
        mappedUser.setEmailId("test@example.com");

        when(userRepo.findByEmailId("test@example.com")).thenReturn(null);
        when(modelMapper.map(userDTO, User.class)).thenReturn(mappedUser);
        when(userRepo.save(mappedUser)).thenReturn(mappedUser);
        when(modelMapper.map(mappedUser, UserDTO.class)).thenReturn(userDTO);

        // Call the method
        UserDTO result = userService.createUser(userDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(userDTO.getEmailId(), result.getEmailId());
    }
    @Test
    public void testCreateUser_UserAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmailId("test@example.com");
        User existingUser = new User();
        existingUser.setEmailId("test@example.com");
        when(userRepo.findByEmailId("test@example.com")).thenReturn(existingUser);
        // Call the method and assert that ResourceAlreadyExist exception is thrown
        assertThrows(ResourceAlreadyExist.class, () -> userService.createUser(userDTO));
    }

}
