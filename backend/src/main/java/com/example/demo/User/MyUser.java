package com.example.demo.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="User")
public class MyUser {
    @Id
    String id;
    String username;
    String password;
}