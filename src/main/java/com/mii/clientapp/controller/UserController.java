package com.mii.clientapp.controller;

import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mii.clientapp.model.Employee;
import com.mii.clientapp.model.User;
import com.mii.clientapp.model.dto.request.UserRequest;
import com.mii.clientapp.service.EmployeeService;
import com.mii.clientapp.service.RoleService;
import com.mii.clientapp.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {

  private UserService userService;
  private EmployeeService employeeService;

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping
  public String index(Model model) {
    model.addAttribute("users", userService.getAll());
    return "user/index";
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping("/{id}")
  public String getById(@PathVariable Long id, Model model) {
    model.addAttribute("user", userService.getById(id));
    return "user/detail";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @GetMapping("/create")
  public String createView(Model model) {
    model.addAttribute("employees", employeeService.getAll());
    return "user/create";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @PostMapping
  public String create(User user) {
    userService.create(user);
    return "redirect:/user";
  }

  // @GetMapping("/create")
  // public String createView(Model model) {
  // model.addAttribute("userRequests", new UserRequest());
  // model.addAttribute("employees", employeeService.getAll());
  // return "user/create";
  // }

  // @PostMapping
  // public String create(UserRequest userRequest) {
  // userService.create(userRequest);
  // return "redirect:/user";
  // }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Long id, Model model) {
    model.addAttribute("user", userService.getById(id));
    return "user/update";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, User user) {
    userService.update(id, user);
    return "redirect:/user";
  }

  @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    userService.delete(id);
    return "redirect:/user";
  }
}
