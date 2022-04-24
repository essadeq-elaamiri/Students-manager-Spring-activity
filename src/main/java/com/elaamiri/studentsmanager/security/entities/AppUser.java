package com.elaamiri.studentsmanager.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private String userId;
    @Column(unique = false)
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    private boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER)
    // un utilisateur peut avoir plusieurs roles
    // EAGER car je souhaite avoir la listes
    // des role de l'utilisateur automatiqument lorsque
    // je charge l'user depuis la base de donée
    // avec LAZY, la liste ne va pas etre charger que
    // quand j'appele user.getAppRoleList ..
    private List<AppRole> appRoleList = new ArrayList<>();
}
