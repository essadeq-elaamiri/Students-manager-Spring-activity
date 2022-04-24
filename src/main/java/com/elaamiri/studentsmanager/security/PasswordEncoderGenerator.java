package com.elaamiri.studentsmanager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderGenerator extends BCryptPasswordEncoder{
        // executé au démarrage, et place
        // l'objet retourné dans le context
        // comme Spring Bean (il peut etre injecté n'import où)
    private PasswordEncoder getPasswordEncoder(){
        // retourner le type d'encodage
        // get spring version
        //System.out.println(SpringVersion.getVersion()); // 5.3.16
        return  new BCryptPasswordEncoder();
    }
}
