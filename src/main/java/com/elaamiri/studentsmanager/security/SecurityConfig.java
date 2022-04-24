package com.elaamiri.studentsmanager.security;

import com.elaamiri.studentsmanager.security.services.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Override // spécifier les droits d'accès
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // ?? for delete operation
        /*  Cross Site Request Forgery (CSRF)
            What is the reason to disable csrf in a Spring Boot application?
            You are using another token mechanism.
            You want to simplify interactions between a client and the server.
            More info: https://www.baeldung.com/spring-security-csrf
            More details: https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/csrf.html
         */
        // droits d'acces
        http.authorizeRequests()
                .antMatchers("/resources/**" ,"/home", "/").permitAll()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/img/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("USER")
                .anyRequest().authenticated(); // PROBLEM SOLVED :
        http.formLogin(); //  default login form
        http.exceptionHandling().accessDeniedPage("/error403");


    }

    @Override // Spécifier la stratégie avec laquelle Spring Sec va
    // chercher les utilisateurs authorisés
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/img/**");
    }


}
