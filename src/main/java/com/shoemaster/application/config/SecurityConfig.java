package com.shoemaster.application.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF and CORS configuration
                .csrf(withDefaults())
                .cors(withDefaults())
                // Configuring endpoint access
                .authorizeHttpRequests((requests) -> requests
                        // Allow access to the root URL for registration
                        .requestMatchers("/", "/login", "/create-account", "/shoe-list", "/error").permitAll()

//                                .requestMatchers("/cart").hasAnyAuthority("USER", "ADMIN")
                        // Configure other URL access rules
                        .requestMatchers("/create-shoe").permitAll()
//                        .requestMatchers("/carts").permitAll()
                        .requestMatchers("/user-list/delete/*").permitAll()
                        .requestMatchers("/users").hasAnyAuthority("USER")
//                        .requestMatchers("/shoe-list").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/secure", "/registered").hasAnyAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
////                 Configuring form login
//                .formLogin(form -> form
//                        .loginPage("/login")  // Assuming '/login' is your actual login page URL
//                        .defaultSuccessUrl("/user-dashboard", true) // Change to your dashboard URL
//                        .permitAll()
//                )

                .formLogin(form -> form
                                .defaultSuccessUrl("/user-list", true) // Set the default success URL here
                                .failureUrl("/login?error=true") // Optional: set a failure URL
                        // You can omit loginPage() and loginProcessingUrl() if using default
                        .permitAll()
                )

                // Logout configuration
//                .logout(withDefaults());

                .logout((logout) -> logout.logoutSuccessUrl("/"));

//        // Configure form login
//                .formLogin(form -> form
//                .loginPage("/login-user")
//                .defaultSuccessUrl("/user-list", true)
//                .permitAll()
//        )

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}