package com.elaamiri.studentsmanager.webControllers;

import com.elaamiri.studentsmanager.entities.Student;
import com.elaamiri.studentsmanager.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/user/students")
    public String showStudentsList(Model model,
                                   @RequestParam(name="keyword", defaultValue = "") String keyword,
                                   @RequestParam(name="page",defaultValue = "0") int page,
                                   @RequestParam(name="size",defaultValue = "7") int size)
    {
        Page<Student> studentsPage = studentService.getStudentsList(keyword, page, size);
        model.addAttribute("studentsLis", studentsPage.getContent());
        if(studentsPage.isEmpty()){
            model.addAttribute("message", "No result found !");
        }
        return "/student/studentsList";
    }
}
