package com.elaamiri.studentsmanager.security.respositories;

import com.elaamiri.studentsmanager.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findAppUserByUsername(String username);
}
