package com.mii.clientapp.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mii.clientapp.model.Country;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryService {

  private RestTemplate restTemplate;

  public List<Country> getAll() {
    return restTemplate
        .exchange("http://localhost:9000/api/country", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Country>>() {
            })
        .getBody();
  }

  public Country getById(Long id) {
    return restTemplate
        .exchange("http://localhost:9000/api/country" + "/" + id, HttpMethod.GET, null,
            new ParameterizedTypeReference<Country>() {
            })
        .getBody();
  }

  public Country create(Country country) {
    return restTemplate
        .exchange("http://localhost:9000/api/country", HttpMethod.POST, new HttpEntity(country), Country.class)
        .getBody();
  }

  public Country update(Long id, Country country) {
    return restTemplate
        .exchange("http://localhost:9000/api/country" + "/" + id, HttpMethod.PUT, new HttpEntity(country),
            Country.class)
        .getBody();
  }

  public Country delete(Long id) {
    return restTemplate
        .exchange("http://localhost:9000/api/country" + "/" + id, HttpMethod.DELETE, null, Country.class).getBody();
  }
}
