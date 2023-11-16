package com.group1.QRPass.service;

import com.group1.QRPass.dto.converter.UserDtoConverter;
import com.group1.QRPass.dto.request.CreateUserRequest;
import com.group1.QRPass.dto.response.LoginResponse;
import com.group1.QRPass.dto.response.UserRegisteredResponse;
import com.group1.QRPass.exception.RoleNotFoundException;
import com.group1.QRPass.exception.UserAlreadyExistException;
import com.group1.QRPass.model.Role;
import com.group1.QRPass.model.User;
import com.group1.QRPass.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AuthenticationServiceTest {
    private static final String TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwiaWF0IjoxNjk4Mzg0MTYxLCJyb2" +
            "xlIjoiIn0.E68EHPWDQ8pq1DkopuUF3UsDX5P0J_6yE74FNf0o3HH3MIBdfQVXhxMxPK7maz9QIJDKPnUmXEwEkN" +
            "ZbpwObfwin2DcwYLvPEQuw7xJb-sTvKOkLdtD6PT5z6rQs_noxp1ZG4uZ2H81qCgsdvfe931q3U43eEKJThTLg_b" +
            "0tb8OLFKwEBdQp9Afq24Ac3Tu0mObtXhzxW7Adx1BQUp85kvvrywsGr_BIEa2vekUFLzF1ouT4U3vRtx0ssJC1n" +
            "3FiqgFTlFD1dctWpIt0bnzhvIgSeYvK4mvHLhRItMXtWD6KN_nn1BCJG5kyZl3CTMwBHExDc3srX_hsYgFsnINr-g";
    private UserService userService;
    private RoleRepository roleRepository;
    private UserDtoConverter userDtoConverter;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp(){
        userService = mock(UserService.class);
        roleRepository = mock(RoleRepository.class);
        userDtoConverter = mock(UserDtoConverter.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        tokenService = mock(TokenService.class);
        authenticationService = new AuthenticationService(userService, roleRepository,userDtoConverter,
                passwordEncoder,
                authenticationManager,
                tokenService);
    }
    @Test
    void registerUser_WhenUserAlreadyExistAndRoleExist_ShouldReturnRegisteredUserResponse(){
        CreateUserRequest createUserRequest = new CreateUserRequest("testUser","123");
        when(userService.isUserExistByName(createUserRequest.username()))
                .thenReturn(false);
        Role role = new Role(1L,"USER");
        when(roleRepository.findByAuthority("USER")).thenReturn(Optional.of(role));
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        String passwordEncoded = "$2y$10$JMMK5fPmYU2mud71i96IeuadncuhzQgIFBcVAlNGqYj5K5FEGe8zy";
        when(passwordEncoder.encode(createUserRequest.password())).thenReturn(passwordEncoded);
        User user = new User(1L, createUserRequest.username(), passwordEncoded,authorities);
        when(userService.createUser(createUserRequest.username(),passwordEncoded,authorities))
                .thenReturn(user);
        UserRegisteredResponse expected = new UserRegisteredResponse(createUserRequest.username());
        when(userDtoConverter.convertToUserRegisteredResponse(user)).thenReturn(expected);
        UserRegisteredResponse actual = authenticationService.registerUser(createUserRequest);
        assertEquals(expected.username(),actual.username());
    }

    @Test
    void registerUser_WhenUserAlreadyExistByName_ThrowUserAlreadyExistException(){
        when(userService.isUserExistByName(Mockito.anyString())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class,
                () -> authenticationService.registerUser(new CreateUserRequest("batu","1234")));
    }

    @Test
    void registerUser_WhenRoleNotExist_ShouldThrowRoleNotFoundException(){
        when(userService.isUserExistByName(Mockito.anyString())).thenReturn(false);
        when(roleRepository.findByAuthority(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class,
                () -> authenticationService.registerUser(new CreateUserRequest("batu","1234")));
    }

    @Test
    void loginUser_WhenCredentialsNotAuthenticated_ShouldThrowAuthenticationServiceException(){
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(AuthenticationServiceException.class);
        assertThrows(AuthenticationServiceException.class,
                () -> authenticationService.loginUser("batu","123"));
    }
    @Test
    void loginUser_WhenCredentialsValid_ShouldReturnLoginResponse(){
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateJwt(authentication))
                .thenReturn(TOKEN);
        LoginResponse expected = new LoginResponse("batu",TOKEN);
        LoginResponse actual = authenticationService.loginUser("batu","123");
        assertEquals(expected.token(),actual.token());
    }
}