package com.example.demo.User;


import com.example.demo.Security.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/githublogin")
@RequiredArgsConstructor
public class GithubController {
    @Value("${github.client.id}")
    String clientId;
    @Value("${github.jwt.secret}")
    String githubSecret;
    private final RestTemplate restTemplate;
    private final MyUserService myUserService;
    private final JWTUtils jwtService;

    @GetMapping()
    ResponseEntity<LoginResponse> getGithubToken(@RequestParam String code){

        String url = "https://github.com/login/oauth/access_token?client_id="
                + clientId + "&client_secret="
                + githubSecret
                + "&code=" + code;

        ResponseEntity<GithubLoginResponse> githubLoginResponseEntity = restTemplate.postForEntity(url, null, GithubLoginResponse.class);

        ResponseEntity<GithubUser> githubuser = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(githubLoginResponseEntity.getBody().getToken())),
                GithubUser.class
        );

        MyUser user = myUserService.createOrGet(githubuser.getBody());

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        String jwt = jwtService.createToken(claims, user.getUsername());

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    private HttpHeaders createHeaders(String token){
        return new HttpHeaders() {{
            String authHeader = "Bearer " + token;
            set( "Authorization", authHeader );
        }};
    }
}
