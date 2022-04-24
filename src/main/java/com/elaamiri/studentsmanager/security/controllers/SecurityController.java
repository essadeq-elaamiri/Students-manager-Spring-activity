package com.elaamiri.studentsmanager.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/error403")
    public String accessDeniedError(){
        return "errors/error403";
    }
}
