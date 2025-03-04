package com.espe.server.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.espe.server.persistence.entity.TokenRecovery;

@Repository
public interface ITokenRecovery extends CrudRepository<TokenRecovery, Long>{
    Optional<TokenRecovery> findByToken(String token);

    void deleteByExpirationTimeBefore(LocalDateTime expirationTime);
}
