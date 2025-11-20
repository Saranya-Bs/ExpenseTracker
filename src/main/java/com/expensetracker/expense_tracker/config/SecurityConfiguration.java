package com.expensetracker.expense_tracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.expensetracker.expense_tracker.filter.JwtAuthFilter;
import com.expensetracker.expense_tracker.service.CustomUserDetailsService;

@Configuration
public class SecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.println("DAO Authentication Provider");
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customUserDetailsService);
        return provider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //System.out.println("Working!!!!!!!!!");

        http
        .csrf(csrf -> csrf.disable()) 
        .authenticationProvider(authenticationProvider())
        .authorizeHttpRequests(auth->auth
            .requestMatchers("/api/users/signup", "/api/users/login").permitAll()
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers("/api/expenses/filter")
                .access((authenticationSupplier,context)->{
                var authentication=authenticationSupplier.get();            
                var a=authentication.getAuthorities();
                boolean isAdmin=a.stream().anyMatch(obj->obj.getAuthority().equals("ROLE_ADMIN"));
                return new AuthorizationDecision(isAdmin);
                })
            .requestMatchers("/api/expenses/paged").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);

        
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    
   
}
