package com.Project.Auth_Vault.Engine.Service;


import com.Project.Auth_Vault.Engine.DTO.SignupRequest;
import com.Project.Auth_Vault.Engine.DTO.SignupResponse;
import com.Project.Auth_Vault.Engine.Entity.Role;
import com.Project.Auth_Vault.Engine.Entity.SignupEntity;
import com.Project.Auth_Vault.Engine.Repository.SignupRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class SignupService {

    final SignupRepository signupRepository;
    final PasswordEncoder passwordEncoder;

    public SignupService(SignupRepository signupRepository, PasswordEncoder passwordEncoder){
        this.signupRepository=signupRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponse saveUser(SignupRequest signupRequest){

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        SignupEntity signupEntity=new SignupEntity(
               signupRequest.getUsername(),
               encodedPassword

        );
        Role assignedRole = signupRequest.getRole() != null ? signupRequest.getRole() : Role.ROLE_USER;
        SignupEntity savedEntity=signupRepository.save(signupEntity);

        return new SignupResponse(
                savedEntity.getUsername()
        );





    }

}
