package com.espe.server.service;

import com.espe.server.persistence.entity.TokenRecovery;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.ITokenRecovery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenRecoveryService {

    private final ITokenRecovery tokenRecovery;

    public TokenRecoveryService(ITokenRecovery tokenRecovery) {
        this.tokenRecovery = tokenRecovery;
    }


    public TokenRecovery saveToken(String token, Usuario usuario, LocalDateTime expirationTime) {
        TokenRecovery tokenRecoveryEntity = new TokenRecovery();
        tokenRecoveryEntity.setToken(token);
        tokenRecoveryEntity.setUsuario(usuario);  
        tokenRecoveryEntity.setExpirationTime(expirationTime);  
        tokenRecoveryEntity.setUsed(false); 

        return tokenRecovery.save(tokenRecoveryEntity);  
    }


    public Optional<TokenRecovery> findByToken(String token) {
        return tokenRecovery.findByToken(token);
    }

    public boolean isTokenValid(String token) {
        Optional<TokenRecovery> tokenOptional = findByToken(token);

        if (tokenOptional.isEmpty()) {
            return false;
        }

        TokenRecovery tokenEntity = tokenOptional.get();

        return !tokenEntity.isUsed() && tokenEntity.getExpirationTime().isAfter(LocalDateTime.now());
    }

    public boolean markTokenAsUsed(String token) {
        Optional<TokenRecovery> tokenOptional = findByToken(token);

        if (tokenOptional.isEmpty()) {
            return false;  
        }

        TokenRecovery tokenEntity = tokenOptional.get();
        tokenEntity.setUsed(true); 

        tokenRecovery.save(tokenEntity); 
        return true;
    }

    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRecovery.deleteByExpirationTimeBefore(now);  
    }
}
