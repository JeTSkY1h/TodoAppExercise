package com.example.demo.User;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubLoginResponse {

    @JsonProperty("access_token")
    private String token;
}
