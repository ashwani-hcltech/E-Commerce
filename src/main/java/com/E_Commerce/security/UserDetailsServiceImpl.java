package com.E_Commerce.security;

import com.E_Commerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Attempting to load user with email: {}", email);
        return userRepository.findByEmail(email)
                .map(user->{
                    logger.info("User found: {}", email);
                    return new CustomUserDetails(user);
                })
                .orElseThrow(()->{
                    logger.warn("User not found with email: {}", email);
                    return new UsernameNotFoundException("User not found with email: "+ email);
                });
    }
}
