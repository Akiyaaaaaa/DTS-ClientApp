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

import com.mii.clientapp.model.Role;
import com.mii.clientapp.service.RoleService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/role")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class RoleController {

  private RoleService roleService;

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping
  public String index(Model model) {
    model.addAttribute("roles", roleService.getAll());
    return "role/index";
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping("/{id}")
  public String getById(@PathVariable Long Id, Model model) {
    model.addAttribute("role", roleService.getById(Id));
    return "role/detail";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @GetMapping("/create")
  public String createView(Role role) {
    return "role/create";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @PostMapping
  public String create(Role role) {
    roleService.create(role);
    return "redirect:/role";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Long id, Model model) {
    model.addAttribute("role", roleService.getById(id));
    return "role/update";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Role role) {
    roleService.update(id, role);
    return "redirect:/role";
  }

  @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    roleService.delete(id);
    return "redirect:/role";
  }
}
