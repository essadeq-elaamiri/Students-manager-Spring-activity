package com.elaamiri.studentsmanager.webControllers;

import com.elaamiri.studentsmanager.entities.GENDER;
import com.elaamiri.studentsmanager.entities.Student;
import com.elaamiri.studentsmanager.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Data
@AllArgsConstructor
public class StudentController {
    private final int MAXPAGES_INRANGE =  5;
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
                                   @RequestParam(name="searchKeyWord", defaultValue = "") String searchKeyWord,
                                   @RequestParam(name="page",defaultValue = "0") int page,
                                   @RequestParam(name="size",defaultValue = "7") int size)
    {
        Page<Student> studentsPage = studentService.getStudentsList(searchKeyWord, page, size);
        model.addAttribute("studentsList", studentsPage.getContent());
        if(studentsPage.isEmpty()){
            model.addAttribute("message", "No result found !");
        }
        model.addAttribute("searchKeyWord", searchKeyWord);
        model.addAttribute("totalElements", studentsPage.getTotalElements());
        model.addAttribute("totalPages", studentsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", new int[studentsPage.getTotalPages()]);

        // TODO: getting the number of males, females and inRules, and send them.
        // TODO: on click on the cards, should return the list.
        long numberOfMales  = studentService.getCountByGender(GENDER.MALE);
        long numberOfFemales = studentService.getCountByGender(GENDER.FEMALE);;
        long inRuleNumber = studentService.getCountByIsInRule(true);

        model.addAttribute("numberOfMales", numberOfMales);
        model.addAttribute("numberOfFemales", numberOfFemales);
        model.addAttribute("inRuleNumber", inRuleNumber);



        int [] pages10 =  fillPagesArray(page, studentsPage.getTotalPages()); // currentPage
        // send an array of  10 elements (pages)
        model.addAttribute("pages10", pages10);
        // the attribute will be accessible from the view

        return "/student/studentsList";
    }

    private int[] fillPagesArray(int currentPage, int numberOfPages){
        /*
        returns an array, filled with the numbers of pages.
        in every call, it returns a list of numbers that contain the currentPage
        ex : currentPage is 9 so it is included in the group 2 because the groupe 1 contains (0-4), g2 (5-10)
        so the array starts from 5 and ends at 10

         */
        // page10Range = (int) currentPage / 10, so I can know with what to fill my array
        int page10Range = (int) (currentPage / MAXPAGES_INRANGE); // find the group of the currentPage
        // init array to be in the size of 5 if the number of pages greater than 5, if not keep it as the number of page
        //int page10Size = numberOfPages / MAXPAGES_INRANGE >= 1 ? MAXPAGES_INRANGE : numberOfPages;
        int page10Size = (numberOfPages - (MAXPAGES_INRANGE * page10Range)) / MAXPAGES_INRANGE >= 1 ? MAXPAGES_INRANGE : (numberOfPages - (MAXPAGES_INRANGE * page10Range)) % MAXPAGES_INRANGE;
        int [] page10= new int[page10Size];
        for (int i= 0; i < page10.length; i++){
            if(((MAXPAGES_INRANGE * page10Range ) + i) >= numberOfPages ){
                break;
            }
            page10[i] = (MAXPAGES_INRANGE * page10Range ) + i;

            //if(currentPage >= numberOfPages) page10[i]=i;
        }
        return page10;
    }

    @GetMapping("/admin/showAddStudent")
    public String showAddStudent(Model model){
        // thymeleaf will access this empty object for binding from data ?
        Student student = new Student();
        model.addAttribute("studentObject", student);
        return "student/addNewStudent";
    }



    //////////////////////



    @PostMapping("/admin/saveNewStudent") // hey you! if a post request this url, call the folloming function
    public String
    saveNewStudent(@ModelAttribute("studentObject") @Valid Student student,
                   BindingResult bindingResult,
                   //@RequestParam(name = "gender")String gender,
                   @RequestParam(name = "page", defaultValue = "0") int page,
                   @RequestParam(name = "size", defaultValue = "5") int size,
                   @RequestParam(name = "searchKeyWord", defaultValue = "")
                           String searchKeyWord
    ){
        return saveStudent(student, bindingResult ,page, size, searchKeyWord, "student/addNewStudent");
    }



    //editStudent
    //deleteStudent
    @GetMapping("/admin/showEditStudent/{id}")// show the view
    public String showEditStudent(@PathVariable(value = "id")Long id,
                              Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              @RequestParam(name = "searchKeyWord", defaultValue = "") String searchKeyWord){
        //get Student from DB
        Optional<Student> student = studentService.getStudentById(id);
        if(student.isEmpty()) throw new RuntimeException("Student with id: "+id+", not found !");
        // send it to the view
        model.addAttribute("studentObject", student.get());
        model.addAttribute("searchKeyWord", searchKeyWord);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "student/editStudent";
    }

    //saveEditedStudent
    @PostMapping("/admin/saveEditedStudent")
    public String saveEditedStudent(@ModelAttribute("studentObject") @Valid Student student,
                                    BindingResult bindingResult,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "5") int size,
                                    @RequestParam(name = "searchKeyWord", defaultValue = "")
                                            String searchKeyWord){

        return saveStudent(student, bindingResult,page, size, searchKeyWord, "student/editStudent");
    }

    private String saveStudent(Student student,
                               BindingResult bindingResult,
                               int page,
                               int size,
                               String searchKeyWord,
                               String ifErrorRedirectTo) {


        if (bindingResult.hasErrors()) {
            return ifErrorRedirectTo; // redirect to form to show errors
        } else {
            studentService.saveNewStudent(student);
            // send a success message
            //model.addAttribute("StudentSuccessInsertionMsg", "Student inserted successfully");
            return "redirect:/user/students?page=" + page + "&size=" + size + "&searchKeyWord=" + searchKeyWord;
        }

    }


    @DeleteMapping("/admin/deleteStudent/{id}")///admin/deleteStudent/510
    //@GetMapping("/admin/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id")Long id,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                @RequestParam(name = "searchKeyWord", defaultValue = "") String searchKeyWord){
        //System.out.println(currentPage);
        studentService.deleteStudent(id);
        System.out.println("from delete: "+ id);
        return "redirect:/user/students?page="+page+"&size="+size+"&searchKeyWord="+(searchKeyWord == null ? "" : searchKeyWord);
    }



}
