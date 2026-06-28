package com.Project.Auth_Vault.Engine.Repository;

import com.Project.Auth_Vault.Engine.Entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface   SignupRepository extends JpaRepository<SignupEntity,Long> {

    Optional<SignupEntity> findByUsername(String username);
//    Optional<Role> findById(String username);

}
