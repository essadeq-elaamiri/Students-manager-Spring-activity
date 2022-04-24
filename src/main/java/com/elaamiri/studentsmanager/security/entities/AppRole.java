package com.elaamiri.studentsmanager.security.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(unique = true)
    @NotNull
    // Je doix pas avoir deux role
    // avec le meme nom
    private String roleName;
    private String roleDescription;

}
