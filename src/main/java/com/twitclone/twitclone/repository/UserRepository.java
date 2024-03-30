package com.twitclone.twitclone.repository;

import com.twitclone.twitclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
