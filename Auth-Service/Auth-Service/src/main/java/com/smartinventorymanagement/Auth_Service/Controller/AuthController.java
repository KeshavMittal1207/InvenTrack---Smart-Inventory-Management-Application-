package com.smartinventorymanagement.Auth_Service.Controller;

import com.smartinventorymanagement.Auth_Service.Dto.LoginRequest;
import com.smartinventorymanagement.Auth_Service.Dto.RegisterRequest;
import com.smartinventorymanagement.Auth_Service.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok("User Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        String token = authService.login(request);
        return ResponseEntity.ok("Bearer " + token);
    }
}
