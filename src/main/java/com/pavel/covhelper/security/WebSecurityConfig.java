package com.pavel.covhelper.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public WebSecurityConfig(@Qualifier("MyUserDetailsService") UserDetailsService  userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/static/**","/css/**").permitAll().antMatchers("/department/{id}/**")
                    .access("hasRole('Admin') or @permissionConfig.checkPermission(authentication, #id)")
                .antMatchers("/**").hasRole("Admin").and().formLogin()
                .loginPage("/login").loginProcessingUrl("/login").permitAll()
                .successHandler(myAuthenticationSuccessHandler()).and().exceptionHandling().accessDeniedPage("/access_denied");
    }

    @Bean
    protected PasswordEncoder passwordEncoder( ) {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public CustomSuccessHandler myAuthenticationSuccessHandler(){
        return new CustomSuccessHandler();
    }

}
