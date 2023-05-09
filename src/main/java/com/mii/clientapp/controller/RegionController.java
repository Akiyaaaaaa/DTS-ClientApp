package com.mii.clientapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mii.clientapp.model.Region;
import com.mii.clientapp.service.RegionService;

import lombok.AllArgsConstructor;
import lombok.Delegate;

@Controller
@RequestMapping("/region")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class RegionController {

  private RegionService regionService;

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping
  public String index(Model model) {
    model.addAttribute("regions", regionService.getAll());
    return "region/index";
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping("/{id}")
  public String getById(@PathVariable Long id, Model model) {
    model.addAttribute("region", regionService.getById(id));
    return "region/detail";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @GetMapping("/create")
  public String createView(Region region) {
    return "region/create-form";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @PostMapping
  public String create(Region region) {
    regionService.create(region);
    return "redirect:/region";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Long id, Model model) {
    model.addAttribute("region", regionService.getById(id));
    return "region/update-form";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Region region) {
    regionService.update(id, region);
    return "redirect:/region";
  }

  @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    regionService.delete(id);
    return "redirect:/region";
  }
}
