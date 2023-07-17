package com.zxw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zxw
 * @date 2022/6/27 21:07
 */
//@Configuration
public class MyWebSecurityConfig {

//    @Autowired
//    private UserDetailsService myUserDetailService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
////        String password = encoder.encode("123456");
////        auth.inMemoryAuthentication().withUser("test").password(password).roles("admin");
//        auth.userDetailsService(myUserDetailService);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().permitAll()
//                .and().authorizeRequests()
////                .antMatchers("/oauth/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().logout().permitAll()
//                .and().csrf().disable();
//    }
}
