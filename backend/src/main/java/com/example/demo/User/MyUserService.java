package com.example.demo.User;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void createNewUser(MyUser newUser){
        String encodedPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(List.of("user"));
        userRepository.save(newUser);
    }

    public Optional<MyUser> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    public MyUser saveUser(MyUser user){
        return userRepository.save(user);
    }

}
