package com.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mii.clientapp.model.Employee;
import com.mii.util.BasicHeader;

@Service
public class EmployeeService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${server.baseUrl}/employee")
  private String url;

  public List<Employee> getAll() {
    return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeader()),
        new ParameterizedTypeReference<List<Employee>>() {
        }).getBody();
  }

  public Employee getById(Long id) {
    return restTemplate.exchange(url + "/" + id, HttpMethod.GET, new HttpEntity(BasicHeader.createHeader()),
        new ParameterizedTypeReference<Employee>() {
        }).getBody();
  }

  public Employee create(Employee employee) {
    return restTemplate
        .exchange(url, HttpMethod.POST, new HttpEntity(employee, BasicHeader.createHeader()), Employee.class)
        .getBody();
  }

  public Employee update(Long id, Employee employee) {
    return restTemplate
        .exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(employee, BasicHeader.createHeader()),
            Employee.class)
        .getBody();
  }

  public Employee delete(Long id) {
    return restTemplate
        .exchange(url + "/" + id, HttpMethod.DELETE, null, Employee.class).getBody();
  }
}
