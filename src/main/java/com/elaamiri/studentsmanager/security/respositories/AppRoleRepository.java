package com.elaamiri.studentsmanager.security.respositories;

import com.elaamiri.studentsmanager.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findAppRoleByRoleName(String roleName);
}
