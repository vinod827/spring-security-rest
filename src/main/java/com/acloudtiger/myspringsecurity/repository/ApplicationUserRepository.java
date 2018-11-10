package com.acloudtiger.myspringsecurity.repository;

import com.acloudtiger.myspringsecurity.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);

}
