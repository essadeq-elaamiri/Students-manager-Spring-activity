package com.elaamiri.studentsmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Override // spécifier les droits d'accès
    protected void configure(HttpSecurity http) throws Exception {

        // droits d'acces
        http.authorizeRequests()
                .antMatchers("/resources/**" ,"/home", "/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated(); // PROBLEM SOLVED :
        // it must be a children under the same  authorizeRequests()
        // order matters
        // allowing static resources by configure(WebSecurity web)
        //.formLogin();




        //http.authorizeRequests().antMatchers("/", "/home").permitAll(); // for all

        // toutes les requets nécessite une authentification
        //http.authorizeHttpRequests().anyRequest().authenticated(); // PROBLEM : prevent anonymous access home

        // hey Spring, je veux utiliser un 'form' d'authetification
        http.formLogin(); //  default login form
        //http.formLogin().loginPage("/login"); // my own login form

        // configure errors (/error403 is a vue)
        http.exceptionHandling().accessDeniedPage("/error403");


    }

    @Override // Spécifier la stratégie avec laquelle Spring Sec va
    // chercher les utilisateurs authorisés
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = getPasswordEncoder();
        // inMemory authentication = les utilisateurs à utiliser l'application
        // seront stockés dans la mémoire (utiles pour les testes)
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER","ADMIN");
        //auth.inMemoryAuthentication() ... on peut reutiliser ...
            /*auth.inMemoryAuthentication()
                    .withUser("user1")
                    .password("{noop}1234") // // noop = no encryption needed
                    .roles("USER");*/


    }
/*
    @Override // Spécifier la stratégie avec laquelle Spring Sec va
    // chercher les utilisateurs authorisés
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = getPasswordEncoder();
        auth.jdbcAuthentication() // il doit établir une connction avec la db
                .dataSource(dataSource)// la même base de donnée de l'app (injectée)
                .usersByUsernameQuery("select" +
                        " username as principal " + // pour que Spring savoir que c'est le username
                        ", password as credentials " +
                        ", isActive " +
                        " from users " +
                        " where username=?") // requete à executée pour récuprirer les users
                .authoritiesByUsernameQuery("select " +
                        " username as principal " +
                        " ,roleName as role " +
                        " from user_role " +
                        " where username = ? ");
                //.rolePrefix("ROLE_"); // 'USER' -> 'ROLE_USER' dans la session
                //.passwordEncoder(passwordEncoder); // algo pour decrypter les passwords


    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/img/**");
    }

    @Bean
        // executé au démarrage, et place
        // l'objet retourné dans le context
        // comme Spring Bean (il peut etre injecté n'import où)
    PasswordEncoder getPasswordEncoder(){
        // retourner le type d'encodage
        // get spring version
        //System.out.println(SpringVersion.getVersion()); // 5.3.16
        return  new BCryptPasswordEncoder();
    }
}
