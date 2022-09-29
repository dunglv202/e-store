package com.example.shopdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService UserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()

                // cart
                .mvcMatchers("/api/v1/carts/**")
                    .hasRole("CUSTOMER")

                // order
                .mvcMatchers("/api/v1/orders/**")
                    .hasRole("CUSTOMER")

                // review
                .mvcMatchers("/api/v1/reviews/**")
                    .hasRole("CUSTOMER")

                // product management
                .mvcMatchers(HttpMethod.POST, "/api/v1/products/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/products/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/products/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/api/v1/products/*/images/**")
                    .hasAnyRole("STOCK_MANAGER", "SALE_EMPLOYEE")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/products/*/images/**")
                    .hasAnyRole("STOCK_MANAGER", "SALE_EMPLOYEE")

                // brands and categories
                .mvcMatchers(HttpMethod.POST, "/api/v1/brands/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/brands/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/brands/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/api/v1/categories/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/categories/**")
                    .hasRole("STOCK_MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/categories/**")
                    .hasRole("STOCK_MANAGER")

                .anyRequest()
                    .permitAll()
                .and()
                .httpBasic();
    }
}
