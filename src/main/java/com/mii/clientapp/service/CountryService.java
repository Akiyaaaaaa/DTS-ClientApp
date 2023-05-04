package com.mii.clientapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mii.clientapp.model.Country;

@Service

public class CountryService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${server.baseUrl}/country")
  private String url;

  public List<Country> getAll() {
    return restTemplate
        .exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Country>>() {
            })
        .getBody();
  }

  public Country getById(Long id) {
    return restTemplate
        .exchange(url + "/" + id, HttpMethod.GET, null,
            new ParameterizedTypeReference<Country>() {
            })
        .getBody();
  }

  public Country create(Country country) {
    return restTemplate
        .exchange(url, HttpMethod.POST, new HttpEntity(country), Country.class)
        .getBody();
  }

  public Country update(Long id, Country country) {
    return restTemplate
        .exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(country),
            Country.class)
        .getBody();
  }

  public Country delete(Long id) {
    return restTemplate
        .exchange(url + "/" + id, HttpMethod.DELETE, null, Country.class).getBody();
  }
}
