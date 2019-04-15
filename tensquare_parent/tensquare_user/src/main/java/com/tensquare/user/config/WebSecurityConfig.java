package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
            @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                        .antMatchers("/**").permitAll()//所有的地址都不会拦截
                        .anyRequest().authenticated()
                        .and().csrf().disable();
            }
        }
