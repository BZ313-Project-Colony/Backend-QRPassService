package com.group1.QRPass.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record CreateUserRequest(  @NotNull(message = "Username cannot be null!")
                                  @NotBlank(message = "Username cannot be blank!")
                                  String username,
                                  @NotNull(message = "Password cannot be null!")
                                  @NotBlank(message = "Password cannot be blank!")
                                  String password) {
}
