package com.sakinramzan.blogapp.repository;

import com.sakinramzan.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends IGenericRepository<User> {
    User findByUsername(String username);
}
