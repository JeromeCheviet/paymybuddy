package com.example.paymybuddy.service;

import com.example.paymybuddy.model.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Class use to connect user with database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method to connect user with his email.
     *
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("logging with email : {}", email);
        User user = Optional.ofNullable(userServiceImpl.getUserByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));
    }

    /**
     * Method to have granted authority.
     *
     * @param user
     * @return List of role. Actually only ADMIN.
     */
    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        logger.debug("get role for user : {}", user.getEmail());
        if (user.isRole()) {
            logger.debug("Grant ADMIN role");
            return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
        }

        logger.debug("Grant USER role");
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }
}
