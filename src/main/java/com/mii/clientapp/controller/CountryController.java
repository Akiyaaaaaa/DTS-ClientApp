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

import com.mii.clientapp.model.Country;
import com.mii.clientapp.service.CountryService;
import com.mii.clientapp.service.RegionService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/country")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class CountryController {

  private CountryService countryService;
  private RegionService regionService;

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping
  public String index(Model model) {
    model.addAttribute("countries", countryService.getAll());
    return "country/index";
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping("/{id}")
  public String getById(@PathVariable Long id, Model model) {
    model.addAttribute("country", countryService.getById(id));
    return "country/detail";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @GetMapping("/create")
  public String createView(Model model, Country country) {
    model.addAttribute("regions", regionService.getAll());
    return "country/create-form";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @PostMapping
  public String create(Country country) {
    countryService.create(country);
    return "redirect:/country";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Long id, Model model) {
    model.addAttribute("country", countryService.getById(id));
    model.addAttribute("regions", regionService.getAll());
    return "country/update-form";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Country country) {
    countryService.update(id, country);
    return "redirect:/country";
  }

  @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    countryService.delete(id);
    return "redirect:/country";
  }

}
