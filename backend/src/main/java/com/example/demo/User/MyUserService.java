package com.example.demo.User;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserService {
    private final UserRepository userRepository;

    public void createNewUser(MyUser newUser){
        userRepository.save(newUser);
    }
}
