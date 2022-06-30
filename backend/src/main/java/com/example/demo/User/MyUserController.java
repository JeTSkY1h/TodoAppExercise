package com.example.demo.User;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MyUserController {

    private final MyUserService myUserService;

    @PostMapping
    public void postNewUser(@RequestBody MyUser newUser){
        myUserService.createNewUser(newUser);
    }

    @GetMapping("me")
    public ResponseEntity<String> getloggedinUser(Principal prinicipal){
        String username = prinicipal.getName();
        return ResponseEntity.ok(myUserService.findByUsername(username).get().getUsername());
    }
}
