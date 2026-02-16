package com.smartinventorymanagement.Auth_Service.Dto;

import lombok.Data;

@Data
public class LoginRequest{
    private String username;
    private String password;
}
