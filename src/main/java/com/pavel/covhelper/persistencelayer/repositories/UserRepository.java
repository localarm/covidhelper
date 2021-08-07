package com.pavel.covhelper.persistencelayer.repositories;

import com.pavel.covhelper.persistencelayer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
