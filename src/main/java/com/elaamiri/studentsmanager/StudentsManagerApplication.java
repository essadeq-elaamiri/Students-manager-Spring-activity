package com.elaamiri.studentsmanager;

import com.elaamiri.studentsmanager.security.services.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsManagerApplication.class, args);
    }

    //@Bean
    CommandLineRunner run(SecurityService securityService){
        return args-> {
            // creating users
            securityService.saveNewUser("user", "user", "user");
            securityService.saveNewUser("admin", "admin", "admin");
            securityService.saveNewRole("USER", "user role");
            securityService.saveNewRole("ADMIN", "admin role");

            securityService.addRoleToUser("user", "USER");
            securityService.addRoleToUser("admin", "USER");
            securityService.addRoleToUser("admin", "ADMIN");
        };
    }
}
