package com.Project.Auth_Vault.Engine.Service;

import com.Project.Auth_Vault.Engine.Entity.BlockListedToken;
import com.Project.Auth_Vault.Engine.Repository.BlockListTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BlockListService {

    final BlockListTokenRepository blockListTokenRepository;

    public BlockListService(BlockListTokenRepository blockListTokenRepository) {
        this.blockListTokenRepository = blockListTokenRepository;
    }


    public void blacklistToken(String token) {
        BlockListedToken blacklisted = new BlockListedToken();
        blacklisted.setBlocklistedToken(token);
        blacklisted.setExpiryDate(Instant.now().plusMillis(86_400_000));
        blockListTokenRepository.save(blacklisted);  // save() default hai
    }

    public boolean isBlacklisted(String token) {
        return blockListTokenRepository.existsByBlocklistedToken(token);
    }


}
