package com.deploy.pertemuan11.controller;

import com.deploy.pertemuan11.model.User;
import com.deploy.pertemuan11.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthService authService;

    @GetMapping("/home")
    public String homePage(Model model) { // Ditambahkan nama method 'homePage'

        User user = authService.getLoggedInUser();

        model.addAttribute("user", user);
        model.addAttribute("profile", user.getProfile());

        return "home";
    }
}