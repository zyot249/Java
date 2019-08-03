package com.zyot.shyn.servicereleasemanagement.config;

import com.zyot.shyn.servicereleasemanagement.common.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST).hasRole(Role.ADMIN)
                .antMatchers(HttpMethod.DELETE).hasRole(Role.ADMIN)
                .antMatchers(HttpMethod.PUT).hasRole(Role.ADMIN)
                .antMatchers(HttpMethod.GET).hasRole(Role.USER)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles(Role.USER)
                .and()
                .withUser("admin").password("{noop}admin").roles(Role.ADMIN, Role.USER);
    }
}
