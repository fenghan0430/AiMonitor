package com.example.aimonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class WebController {

    @GetMapping("/login")
    public String loginWeb(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardWeb(){
        return "dashboard";
    }
}
