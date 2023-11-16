package com.group1.QRPass.service;

import com.group1.QRPass.model.Role;
import com.group1.QRPass.model.User;
import com.group1.QRPass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;
    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void loadByUsername_WhenUsernameExist_ShouldReturnUserDetails(){
        String username = "abcd";
        User expected = new User();
        expected.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expected));
        UserDetails actual = userService.loadUserByUsername(username);
        assertEquals(expected,actual);
    }

    @Test
    void loadByUsername_WhenUsernameNotExist_ShouldThrowUsernameNotFoundException(){
        String username = "abcd";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,() -> userService.loadUserByUsername(username));
    }

    @Test
    void createUser_ShouldReturnUser(){
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        User user = userService.createUser("batuhan","1234", Set.of(new Role(1L,"USER")));
        assertNotNull(user);
        verify(userRepository).save(argThat(argument -> argument.getUsername().equals("batuhan")));
    }

    @Test
    void isUserExistByName_WhenUsernameExist_ShouldReturnTrue(){
        String username = "Batuhan";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));
        boolean result = userService.isUserExistByName(username);
        assertTrue(result);
    }

    @Test
    void isUserExistByName_WhenUsernameNotExist_ShouldReturnFalse(){
        String username = "Batuhan";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        boolean result = userService.isUserExistByName(username);
        assertFalse(result);
    }
}
