package com.Project.Auth_Vault.Service;


import com.Project.Auth_Vault.DTO.SignupRequest;
import com.Project.Auth_Vault.DTO.SignupResponse;
import com.Project.Auth_Vault.Entity.SignupEntity;
import com.Project.Auth_Vault.Repository.SignupRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    final SignupRepository signupRepository;
    final PasswordEncoder passwordEncoder;

    public SignupService(SignupRepository signupRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1){
        this.signupRepository=signupRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponse saveUser(SignupRequest signupRequest){

//        encode password
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

//        map data to entity
       SignupEntity signupEntity=new SignupEntity(
               signupRequest.getUsername(),
               encodedPassword

       );

        SignupEntity savedEntity=signupRepository.save(signupEntity);

        return new SignupResponse(
                savedEntity.getUsername()
        );



    }

}
