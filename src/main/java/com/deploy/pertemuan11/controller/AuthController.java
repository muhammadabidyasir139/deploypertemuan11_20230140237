package com.deploy.pertemuan11.controller;

import com.deploy.pertemuan11.model.dto.RegisterRequest;
import com.deploy.pertemuan11.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping; // Ditambahkan titik koma (;)

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/register")
    public String registerPage(Model model) { // Ditambahkan nama method 'registerPage'

        model.addAttribute("request", new RegisterRequest());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest request) { // Ditambahkan nama method 'registerUser'

        authService.register(request);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}