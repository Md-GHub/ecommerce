package com.mdghub.project.repository;

import com.mdghub.project.model.AppRole;
import com.mdghub.project.model.Roles;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<Roles, Integer> {
    Optional<Roles> findByRoleName(AppRole appRole);
}
