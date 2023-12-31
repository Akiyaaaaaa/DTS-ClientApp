package com.mii.clientapp.service;

import com.mii.clientapp.model.Region;
import java.util.List;

import com.mii.clientapp.util.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegionService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${server.baseUrl}/region")
  private String url;

  public List<Region> getAll() {
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Authorization","basic am9oYW46am9oYW5wYXNz"); // johan:johanpass
    return restTemplate
      .exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Region>>() {}
      )
      .getBody();
  }

  public Region getById(Long id) {
    return restTemplate
      .exchange(
        url.concat("/" + id),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Region>() {}
      )
      .getBody();
  }

  public Region create(Region region) {
    return restTemplate
      .exchange(url, HttpMethod.POST, new HttpEntity(region), Region.class)
      .getBody();
  }

  public Region update(long id, Region region) {
    return restTemplate
      .exchange(
        url + "/" + id,
        HttpMethod.PUT,
        new HttpEntity(region),
        Region.class
      )
      .getBody();
  }

  public Region delete(long id) {
    return restTemplate
      .exchange(url + "/" + id, HttpMethod.DELETE, null, Region.class)
      .getBody();
  }
}
