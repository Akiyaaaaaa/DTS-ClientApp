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

import com.mii.clientapp.model.Employee;
import com.mii.clientapp.service.EmployeeService;
import com.mii.clientapp.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class EmployeeController {

  private EmployeeService employeeService;
  private UserService userService;

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping
  public String index(Model model) {
    model.addAttribute("employees", employeeService.getAll());
    return "employee/index";
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
  @GetMapping("/{id}")
  public String getById(@PathVariable Long id, Model model) {
    model.addAttribute("employee", employeeService.getById(id));
    return "employee/detail";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @GetMapping("/create")
  public String createView(Model model, Employee employee) {
    model.addAttribute("users", userService.getAll());
    return "employee/create";
  }

  @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
  @PostMapping
  public String create(Employee employee) {
    employeeService.create(employee);
    return "redirect:/employee";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Long id, Model model) {
    model.addAttribute("employee", employeeService.getById(id));
    return "employee/update";
  }

  @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
  @PutMapping("/{id}")
  public String update(@PathVariable Long id, Employee employee) {
    employeeService.update(id, employee);
    return "redirect:/employee";
  }

  @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    employeeService.delete(id);
    return "redirect:/employee";
  }
}
