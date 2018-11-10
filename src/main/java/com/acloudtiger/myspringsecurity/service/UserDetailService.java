package com.acloudtiger.myspringsecurity.service;

import com.acloudtiger.myspringsecurity.dto.UserDto;
import com.acloudtiger.myspringsecurity.entity.ApplicationUser;
import com.acloudtiger.myspringsecurity.entity.User;
import com.acloudtiger.myspringsecurity.repository.ApplicationUserRepository;
import com.acloudtiger.myspringsecurity.repository.UserDetailJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
public class UserDetailService implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    private UserDetailJPA userDetailJPA;

    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserDetailService(UserDetailJPA userDetailJPA, ApplicationUserRepository applicationUserRepository) {
        this.userDetailJPA = userDetailJPA;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Entering getAllUsers@UserDetailService");
        List<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        if(!StringUtils.isEmpty(applicationUsers)){
            List<UserDto> newList = applicationUsers
                    .parallelStream()
                    .map(obj -> new UserDto(obj.getId(), null, null, obj.getUsername(), obj.getPassword(), null))
                    .collect(Collectors.toList());

            return newList;
        }
        return null;

    }

    @Override
    public UserDto getUserById(int id) {
        logger.info("Entering getUserById@UserDetailService");
        Optional<User> user = userDetailJPA.findById(id);
        if(user.isPresent()){
          UserDto userDto = new UserDto(user.get().getUserId(),
                  user.get().getFirstName(), user.get().getLastName(), null, null, null);
          return userDto;
        }
         return null;
    }

    @Override
    public void addUser(UserDto user) {
        User usr = new User();
        usr.setUserId(user.getId());
        usr.setFirstName(user.getFirstName());
        usr.setLastName(user.getLastName());
        userDetailJPA.save(usr);

    }


}
