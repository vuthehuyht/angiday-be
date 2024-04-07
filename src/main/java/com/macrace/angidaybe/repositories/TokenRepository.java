package com.macrace.angidaybe.repositories;

import com.macrace.angidaybe.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query(value = "select * from token t where t.expired = false and t.user_id = :userId", nativeQuery = true)
    List<Token> findAllTokenByUserId(Long userId);
}
