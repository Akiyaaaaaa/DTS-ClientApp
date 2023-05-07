package com.mii.clientapp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mii.clientapp.model.dto.request.LoginRequest;
import com.mii.clientapp.service.LoginService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

  private LoginService loginService;

  @GetMapping
  public String loginView(LoginRequest loginRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken) {
      return "auth/login";
    }
    return "redirect:/region";
  }

  @PostMapping
  public String login(LoginRequest loginRequest) {
    if (!loginService.login(loginRequest)) {
      return "redirect:/login?error=true";
    }
    return "redirect:/region";
  }
}
