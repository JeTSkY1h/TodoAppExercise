package com.example.demo.User;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<MyUser, String> {
    
    public Optional<MyUser> findByUsername(String username);

}