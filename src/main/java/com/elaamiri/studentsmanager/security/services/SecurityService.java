package com.elaamiri.studentsmanager.security.services;

import com.elaamiri.studentsmanager.security.entities.AppRole;
import com.elaamiri.studentsmanager.security.entities.AppUser;
import com.elaamiri.studentsmanager.security.entities.AppUser;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    AppUser saveNewUser(String username, String password, String passwordConfirmation);
    AppRole saveNewRole(String roleName, String description);
    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);
    void changeUserActiveStatus(String username, boolean newStatus);

    AppUser loadUserByUsername(String username);

}
