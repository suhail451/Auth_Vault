package com.Project.Auth_Vault.Repository;

import com.Project.Auth_Vault.Entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
LoginRepository extends JpaRepository<SignupEntity,Long> {

     Optional<SignupEntity> findByusername(String username);
}
