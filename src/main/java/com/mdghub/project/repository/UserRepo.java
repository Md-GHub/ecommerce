package com.mdghub.project.repository;

import com.mdghub.project.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long>{
    Optional<Users> findByUserName(String username);

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);
}
