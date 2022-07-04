package com.example.demo.User;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MongoUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).map(user -> new User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("user"))))
        .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }
}