package com.Project.Auth_Vault.Engine.Service;

import com.Project.Auth_Vault.Engine.Entity.SignupEntity;
import com.Project.Auth_Vault.GlobalException.UserNotFoundException;
import com.Project.Auth_Vault.Engine.Repository.SignupRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SignupRepository signupRepository;

    public UserDetailsServiceImpl(SignupRepository signupRepository) {
        this.signupRepository = signupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        SignupEntity signupEntity = signupRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));


        var authorities = signupEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());


        return new User(
                signupEntity.getUsername(),
                signupEntity.getPassword(),
                authorities
        );
    }
}