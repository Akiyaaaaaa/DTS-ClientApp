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

import com.mii.clientapp.model.Role;
import com.mii.clientapp.service.RoleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/role")
public class RestRoleController {

  private RoleService roleService;

  @GetMapping
  public List<Role> getAll() {
    return roleService.getAll();
  }

  @GetMapping("/{id}")
  public Role getById(@PathVariable Long id) {
    return roleService.getById(id);
  }

  @PostMapping
  public Role create(@RequestBody Role role) {
    return roleService.create(role);
  }

  @PutMapping("{id}")
  public Role update(@PathVariable Long id, @RequestBody Role role) {
    return roleService.update(id, role);
  }

  @DeleteMapping("/{id}")
  public Role delete(@PathVariable Long id) {
    return roleService.delete(id);
  }

}
