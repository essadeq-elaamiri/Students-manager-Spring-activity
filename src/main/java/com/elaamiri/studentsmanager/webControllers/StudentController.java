package com.elaamiri.studentsmanager.webControllers;

import com.elaamiri.studentsmanager.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Data
@AllArgsConstructor
public class StudentController {
    StudentService studentService;

    //home and /
    @GetMapping("/home")
    public String showHomePage(){
        return "homePage";
    }
    @GetMapping("/")
    public String handleRootPath(){
        return "redirect:/home";
    }
}
