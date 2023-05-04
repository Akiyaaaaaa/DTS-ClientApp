package com.mii.clientapp.controller.Rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mii.clientapp.model.Region;
import com.mii.clientapp.service.RegionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/region")
public class RestRegionController {

  private RegionService regionService;

  @GetMapping
  public List<Region> getAll() {
    return regionService.getAll();
  }

  @GetMapping("/{id}")
  public Region getById(@PathVariable Long id) {
    return regionService.getById(id);
  }

  @PostMapping
  public Region create(@RequestBody Region region) {
    return regionService.create(region);
  }

  @PutMapping("/{id}")
  public Region update(@PathVariable Long id, @RequestBody Region region) {
    return regionService.update(id, region);
  }

  @DeleteMapping("/{id}")
  public Region delete(@PathVariable Long id) {
    return regionService.delete(id);
  }
}
