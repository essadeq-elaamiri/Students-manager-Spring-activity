package com.elaamiri.studentsmanager.entities;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
public class Student {

    // TODO: validations
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String firstName;
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String lastName;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birth;
    private GENDER gender;
    private boolean inRule; // an règle

}
