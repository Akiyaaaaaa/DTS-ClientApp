package com.mii.clientapp.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mii.clientapp.model.Region;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegionService {

  private RestTemplate restTemplate;

  public List<Region> getAll() {
    return restTemplate.exchange("http://localhost:9000/api/region", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Region>>() {
        }).getBody();
  }

  public Region getById(Long id) {
    return restTemplate
        .exchange("http://localhost:9000/api/region" + "/" + id, HttpMethod.GET, null,
            new ParameterizedTypeReference<Region>() {
            })
        .getBody();
  }

  public Region create(Region region) {
    return restTemplate
        .exchange("http://localhost:9000/api/region", HttpMethod.POST, new HttpEntity(region), Region.class).getBody();
  }

  public Region update(Long id, Region region) {
    return restTemplate
        .exchange("http://localhost:9000/api/region" + "/" + id, HttpMethod.PUT, new HttpEntity(region), Region.class)
        .getBody();
  }

  public Region delete(Long id) {
    return restTemplate
        .exchange("http://localhost:9000/api/region" + "/" + id, HttpMethod.DELETE, null, Region.class).getBody();
  }
}
