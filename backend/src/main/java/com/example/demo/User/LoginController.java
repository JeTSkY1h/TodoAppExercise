package com.example.demo.User;

import java.util.*;

import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.demo.JWTUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final MyUserService myUserService;
    private final JWTUtils jwtUtils;
    
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginData login){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            MyUser user = myUserService.findByUsername(login.getUsername()).orElseThrow();
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", user.getRoles());
            return ResponseEntity.ok(new LoginResponse(jwtUtils.createToken(claims, login.getUsername())));
        } catch(Exception e){
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}