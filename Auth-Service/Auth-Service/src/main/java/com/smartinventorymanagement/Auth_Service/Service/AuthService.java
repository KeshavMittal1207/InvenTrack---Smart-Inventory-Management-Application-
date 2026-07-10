package com.smartinventorymanagement.Auth_Service.Service;

import com.smartinventorymanagement.Auth_Service.Dto.AuthResponse;
import com.smartinventorymanagement.Auth_Service.Dto.LoginRequest;
import com.smartinventorymanagement.Auth_Service.Dto.RegisterRequest;
import com.smartinventorymanagement.Auth_Service.Model.User;
import com.smartinventorymanagement.Auth_Service.Repository.UserRepository;
import com.smartinventorymanagement.Auth_Service.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(existingUser -> {
                    throw new RuntimeException("Username already exists");
                });

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return new AuthResponse(
                JwtUtil.generateToken(user.getUsername()),
                user.getUsername()
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        return new AuthResponse(
                JwtUtil.generateToken(user.getUsername()),
                user.getUsername()
        );
    }
}
