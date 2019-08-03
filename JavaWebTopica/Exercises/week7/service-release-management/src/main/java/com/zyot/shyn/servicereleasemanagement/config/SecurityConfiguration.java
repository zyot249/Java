package com.zyot.shyn.servicereleasemanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/release/**").permitAll()
                .antMatchers("/api/service/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("release/**/service/**").permitAll()
                .antMatchers("release/**/**").permitAll()
                .antMatchers("/release/**").permitAll()
                .antMatchers("/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
