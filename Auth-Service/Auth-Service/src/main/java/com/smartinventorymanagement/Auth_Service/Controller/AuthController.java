package com.smartinventorymanagement.Auth_Service.Controller;
import com.smartinventorymanagement.Auth_Service.Dto.AuthRequest;
import com.smartinventorymanagement.Auth_Service.Dto.AuthResponse;
import com.smartinventorymanagement.Auth_Service.Model.User;
import com.smartinventorymanagement.Auth_Service.Security.JwtUtil;
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
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        User user = authService.login(request);
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
