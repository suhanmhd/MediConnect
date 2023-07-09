package com.example.mediconnect.AuthService.config;


import com.example.mediconnect.AuthService.entity.UserCredential;
import com.example.mediconnect.AuthService.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
    public class UserInfoUserDetailsService implements UserDetailsService {

      @Autowired
        private UserCredentialRepository repository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<UserCredential> userInfo = repository.findByName(username);
            return userInfo.map(UserInfoUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

        }
    }
