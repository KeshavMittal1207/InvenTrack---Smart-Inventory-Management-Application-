package com.smartinventorymanagement.Auth_Service.Service;

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

    public void register(RegisterRequest request){
        User user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).build();
        userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return JwtUtil.generateToken(user.getUsername());
    }

}
