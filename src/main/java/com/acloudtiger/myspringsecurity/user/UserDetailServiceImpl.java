package com.acloudtiger.myspringsecurity.user;

import com.acloudtiger.myspringsecurity.entity.ApplicationUser;
import com.acloudtiger.myspringsecurity.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class UserDetailServiceImpl implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    public UserDetailServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        if(applicationUser == null){
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());
    }
}
