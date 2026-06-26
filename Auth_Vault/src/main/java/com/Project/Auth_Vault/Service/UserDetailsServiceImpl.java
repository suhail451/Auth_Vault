package com.Project.Auth_Vault.Service;

import com.Project.Auth_Vault.Entity.SignupEntity;
import com.Project.Auth_Vault.Repository.SignupRepository;
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

        // Step 1 — database mein username se user dhundo

        SignupEntity signupEntity = signupRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Step 2 — roles ko Spring Security ke format mein convert karo
        //Role enum hai → SimpleGrantedAuthority mein dalna hoga
        // kyunki Spring Security SimpleGrantedAuthority samajhta hai,enum nahi
        var authorities = signupEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        // Step 3 — Spring Security ka User object banao aur return karo
        // ye wahi "badge" hai jo JwtAuthFilter SecurityContext mein lagata hai
        // User(username, password, authorities) — teen cheezein
        return new User(
                signupEntity.getUsername(),
                signupEntity.getPassword(),
                authorities
        );
    }
}