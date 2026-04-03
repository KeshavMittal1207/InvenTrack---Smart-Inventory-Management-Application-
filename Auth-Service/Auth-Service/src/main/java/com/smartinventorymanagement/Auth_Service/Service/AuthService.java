package com.smartinventorymanagement.Auth_Service.Service;

import com.smartinventorymanagement.Auth_Service.Dto.AuthRequest;
import com.smartinventorymanagement.Auth_Service.Model.User;
import com.smartinventorymanagement.Auth_Service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(AuthRequest request){
        User user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword()))
        .enabled(true).role("STAFF").build();
        return userRepository.save(user);
    }

    public User login(AuthRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return user;
    }

}
