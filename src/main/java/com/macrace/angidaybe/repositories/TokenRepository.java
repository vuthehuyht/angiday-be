package com.macrace.angidaybe.repositories;

import com.macrace.angidaybe.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
//    @Query(value = "select t from token t inner join user u on t.user.id = u.id where u.id = :userId and (t.expired = false or t.revoked = false)")
//    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);
}
