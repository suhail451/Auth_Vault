package com.Project.Auth_Vault.Repository;

import com.Project.Auth_Vault.Entity.Role;
import com.Project.Auth_Vault.Entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface   SignupRepository extends JpaRepository<SignupEntity,Long> {

    Optional<SignupEntity> findByUsername(String username);
//    Optional<Role> findById(String username);

}
