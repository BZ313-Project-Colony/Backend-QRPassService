package com.group1.QRPass.dto.converter;

import com.group1.QRPass.dto.response.UserRegisteredResponse;
import com.group1.QRPass.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public UserRegisteredResponse convertToUserRegisteredResponse(User user){
        return new UserRegisteredResponse(user.getUsername());
    }
}
