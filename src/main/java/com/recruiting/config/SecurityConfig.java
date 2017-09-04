package com.recruiting.config;

import com.recruiting.handler.SecuritySuccessHandler;
import com.recruiting.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by Martha on 4/5/2017.
 */
//public class SecurityConfig{
@Configuration
@EnableWebMvc
@Order
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userService")
    UserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .invalidSessionUrl(Constants.PATH_BASE_LOGIN)
                .and()
                .rememberMe()
                .rememberMeParameter(Constants.KEY_REMEMBER_ME)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(20)
                .and()
                .formLogin()
                .loginPage(Constants.PATH_BASE_LOGIN)
                .successHandler(new SecuritySuccessHandler())
                .usernameParameter(Constants.KEY_USERNAME)
                .passwordParameter(Constants.KEY_PASSWORD)
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .and()
                .csrf()
                .disable();
        System.out.println();
    }


    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers(
                        "/resources/**",
                        "/resources/templates/registration/**",
                        "/resources/templates/**",
                        "/resources/templates",
                        "/resources/templates/",
                        "/", "/registration",
                        "/templates/registration/**",
                        "/registration/**",
                        "/registration/",
                        "registration",
                        "/templates/registration/company/**",
                        "/templates/registration/company",
                        "/templates/registration/candidate/**",
                        "/templates/registration/candidate",
                        "registration/candidate",
                        "registration/company");
    }

    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
        PersistentTokenBasedRememberMeServices tokenBasedService = new PersistentTokenBasedRememberMeServices(
                "remember-me", userDetailsService, persistentTokenRepository());
        tokenBasedService.setUseSecureCookie(true);
        tokenBasedService.setCookieName("remember-me");
        tokenBasedService.setTokenLength(3);
        return tokenBasedService;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
