package com.mii.clientapp.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.swing.plaf.synth.Region;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mii.clientapp.model.Employee;
import com.mii.clientapp.model.User;
import com.mii.clientapp.model.dto.request.UserRequest;
import com.mii.util.BasicHeader;

@Service
public class UserService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${server.baseUrl}/user")
  private String url;

  public List<User> getAll() {
    return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeader()),
        new ParameterizedTypeReference<List<User>>() {

        }).getBody();
  }

  public User getById(Long id) {
    return restTemplate.exchange(url + "/" + id, HttpMethod.GET, new HttpEntity(BasicHeader.createHeader()),
        new ParameterizedTypeReference<User>() {

        }).getBody();
  }

  public User create(User user) {
    return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(user), User.class).getBody();
  }
  // public User create(UserRequest userRequest) {
  // User user = new User();
  // user.setUsername(userRequest.getUsername());
  // user.setPassword(userRequest.getPassword());
  // Employee employee = new Employee();
  // employee.setName(userRequest.getName());
  // employee.setEmail(userRequest.getEmail());
  // employee.setPhone(userRequest.getPhone());
  // user.setEmployee(employee);
  // return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(user),
  // User.class).getBody();

  // }

  public User update(Long id, User user) {
    return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(user), User.class).getBody();
  }

  public User delete(Long id) {
    return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, null, User.class).getBody();
  }
}
