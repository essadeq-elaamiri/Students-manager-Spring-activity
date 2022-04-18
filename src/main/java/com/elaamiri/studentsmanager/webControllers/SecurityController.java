package com.elaamiri.studentsmanager.webControllers;

import org.springframework.web.bind.annotation.GetMapping;

public class SecurityController {
    @GetMapping("/error403")
    public String accessDeniedError(){
        return "errors/error403";
    }
}
