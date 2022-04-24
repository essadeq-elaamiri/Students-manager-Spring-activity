package com.elaamiri.studentsmanager.security.services;

import com.elaamiri.studentsmanager.security.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private SecurityService securityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user
        AppUser appUser = securityService.loadUserByUsername(username);
        // il faut retourner un objet UserDetails
        // Collection pour les role
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        /*
        appUser.getAppRoleList().forEach(appRole -> {
            // de AppRole as String vers simpleGrantedAuthority (GrantedAuthority)
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(appRole.getRoleName());
            grantedAuthorities.add(simpleGrantedAuthority);
        });
        */
        // en utilisant l'API Stream
        grantedAuthorities = appUser
                .getAppRoleList()
                .stream()
                .map(appRole -> {
                    return new SimpleGrantedAuthority(appRole.getRoleName());
                }).collect(Collectors.toList());

        // créer un User de Spring Security
        //org.springframework.security.core.userdetails.User;
        // de AppUser vers User (UserDetails)
        User springSecurityUser = new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
        return springSecurityUser;
    }
}
