package com.mii.clientapp.service;

import com.mii.clientapp.model.dto.request.LoginRequest;
import com.mii.clientapp.model.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private RestTemplate restTemplate;

    @Autowired
    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/login")
    private String url;

    public Boolean login(LoginRequest loginRequest){
        ResponseEntity<LoginResponse> res = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity(loginRequest),
                new ParameterizedTypeReference<LoginResponse>() {
                }
        );
        if (res.getStatusCode() == HttpStatus.OK){
            setAuthentication(res.getBody(), loginRequest.getPassword());
            return true;
        }
        return false;
    }

    public void setAuthentication(LoginResponse res, String password){
        List<SimpleGrantedAuthority> authorities = res.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(res.getUsername(), password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
