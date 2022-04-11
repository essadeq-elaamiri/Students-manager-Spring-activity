package com.elaamiri.studentsmanager.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Student {

    // TODO: validations
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date birth;
    private GENDER gender;
    private boolean inRule; // an règle

}
