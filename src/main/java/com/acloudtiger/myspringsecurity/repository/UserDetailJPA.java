package com.acloudtiger.myspringsecurity.repository;

import com.acloudtiger.myspringsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailJPA extends JpaRepository<User, Integer> {

}