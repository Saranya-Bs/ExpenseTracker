// package com.expensetracker.expense_tracker.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;


// @Configuration
// public class InMemory {
//       @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails user = User
//                 .withUsername("user")
//                 .password("{noop}pwd@123")
//                 .roles("USER")
//                 .build();

//         UserDetails admin = User
//                 .withUsername("saranya")
//                 .password("{noop}saranya@123")
//                 .authorities("READ_EXPENSES")
//                 .roles("ADMIN")
//                 .build();

//         // System.out.println(admin.getAuthorities());

//         return new InMemoryUserDetailsManager(user, admin);
//     }

    

    

// }
