package com.example.demo.User;


import lombok.RequiredArgsConstructor;

import java.util.HashMap;
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

    public MyUser createOrGet(GithubUser gitUser){
        Optional<MyUser> gotUser = userRepository.findByGitHubUserId(gitUser.getId());
        if(gotUser.isEmpty()){
            MyUser newUser = new MyUser();
            newUser.setGitHubUserId(gitUser.getId());
            newUser.setRoles(List.of("user"));
            newUser.setUsername(gitUser.getLogin());
            return userRepository.save(newUser);
        }
        return gotUser.get();
    }

    public Optional<MyUser> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    public MyUser saveUser(MyUser user){
        return userRepository.save(user);
    }

}
