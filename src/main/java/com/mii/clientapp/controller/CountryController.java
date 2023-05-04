package com.mii.clientapp.controller;

import com.mii.clientapp.model.Country;
import com.mii.clientapp.service.CountryService;
import com.mii.clientapp.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/country")
public class CountryController {

  private CountryService countryService;
  private RegionService regionService;

  @GetMapping
  public String index(Model model) {
    model.addAttribute("countries", countryService.getAll());
    return "country/index";
  }

  @GetMapping("/{id}")
  public String getById(@PathVariable long id, Model model) {
    model.addAttribute("country", countryService.getById(id));
    return "country/detail";
  }

  @GetMapping("/create")
  public String createView(Country country, Model model) {
    model.addAttribute("regions", regionService.getAll());
    return "country/create-form";
  }

  @PostMapping
  public String create(Country country) {
    countryService.create(country);
    return "redirect:/country";
  }

  @GetMapping("/update/{id}")
  public String updateView(@PathVariable long id, Model model) {
    model.addAttribute("regions", regionService.getAll());
    model.addAttribute("country", countryService.getById(id));
    return "country/update-form";
  }

  @PutMapping("/{id}")
  public String update(@PathVariable long id, Country country) {
    countryService.update(id, country);
    return "redirect:/country";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable long id) {
    countryService.delete(id);
    return "redirect:/country";
  }
}
