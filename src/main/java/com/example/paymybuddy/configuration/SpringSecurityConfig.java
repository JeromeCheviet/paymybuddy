package com.example.paymybuddy.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class to configure authentication and authorization with Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LogManager.getLogger(SpringSecurityConfig.class);

    //private static final String ADMIN = "ADMIN";

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService customUserDetailsService;

    /**
     * {@inheritDoc}
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("HTTP Security");
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/signup", "/newUser", "/css/**").permitAll()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    /**
     * {@inheritDoc}
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("Authentication Manager.");
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Method to encode password with BCrypt hash.
     * @return encoded passeword.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Encode password");
        return new BCryptPasswordEncoder();
    }

}
