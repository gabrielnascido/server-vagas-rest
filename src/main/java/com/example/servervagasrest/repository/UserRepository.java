package com.example.servervagasrest.repository;

import com.example.servervagasrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Verifica se um user existe pelo username.
     * @param username O username a ser verificado.
     * @return true se existir, false caso contrário.
     */
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);


}