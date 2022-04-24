package com.elaamiri.studentsmanager.security.services;

import com.elaamiri.studentsmanager.security.entities.AppRole;
import com.elaamiri.studentsmanager.security.entities.AppUser;
import com.elaamiri.studentsmanager.security.respositories.AppRoleRepository;
import com.elaamiri.studentsmanager.security.respositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
// pour l'injection par contructeur
@Slf4j // logging purposes
@Transactional
// spring will manage the transactions
// all the methods are transactional !!
// more details:
// https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String passwordConfirmation) {
        // verifying if the username is already there
        Optional<AppUser> appUserByName = appUserRepository.findAppUserByUsername(username);
        if(appUserByName.isPresent()){
            throw  new RuntimeException("The username "+username+" is already taken!");
        }
        // verifying username and password
        if(username.length()<3) throw new RuntimeException("Username should be longer than 3 chars");
        if(!password.equals(passwordConfirmation)) throw new RuntimeException("Password does not match !!");
        String hashedPass = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(hashedPass);
        appUser.setActive(true);
        appUser.setUserId(UUID.randomUUID().toString());
        AppUser saveAppUser = appUserRepository.save(appUser);
        return saveAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = new AppRole();
        // verifying if the role already exists
        Optional<AppRole> appRoleByName = appRoleRepository.findAppRoleByRoleName(roleName);
        if(appRoleByName.isPresent()){
            throw  new RuntimeException("The role " +roleName+ " already exist!");
        }
        appRole.setRoleName(roleName);
        appRole.setRoleDescription(description);
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Optional<AppUser> appUser = appUserRepository.findAppUserByUsername(username);
        Optional<AppRole> appRole = appRoleRepository.findAppRoleByRoleName(roleName);
        if(appUser.isEmpty()){
            throw  new RuntimeException("User "+username+" not found!");
        }
        if(appRole.isEmpty()){
            throw  new RuntimeException("Role "+roleName+" not found!");
        }

        appUser.get().getAppRoleList().add(appRole.get());
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        Optional<AppUser> appUser = appUserRepository.findAppUserByUsername(username);
        Optional<AppRole> appRole = appRoleRepository.findAppRoleByRoleName(roleName);
        if(appUser.isEmpty()){
            throw  new RuntimeException("User "+username+" not found!");
        }
        if(appRole.isEmpty()){
            throw  new RuntimeException("Role "+roleName+" not found!");
        }

        appUser.get().getAppRoleList().remove(appRole.get());
    }

    @Override
    public void changeUserActiveStatus(String username, boolean newStatus) {
        Optional<AppUser> appUser = appUserRepository.findAppUserByUsername(username);
        if(appUser.isEmpty()){
            throw  new RuntimeException("User "+username+" not found!");
        }
        appUser.get().setActive(newStatus);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username).orElseGet(null);
    }


}
