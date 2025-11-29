package com.example.servervagasrest.service;

import com.example.servervagasrest.controller.dto.ComumUserUpdateDTO;
import com.example.servervagasrest.exception.ManualValidationException;
import com.example.servervagasrest.exception.UserNotFoundException;
import com.example.servervagasrest.model.ComumUser;
import com.example.servervagasrest.repository.UserRepository;
import com.example.servervagasrest.exception.UsernameConflictException;
import org.springframework.stereotype.Service;

@Service
public class ComumUserService {

    private final UserRepository repository;

    public ComumUserService(UserRepository repository) {
        this.repository = repository;
    }

    public ComumUser create(ComumUser user) {

        if (repository.existsByUsername(user.getUsername())) {
            throw new UsernameConflictException("Username already exists");
        }

        return repository.save(user);
    }

    public ComumUser findById(Long id) {
        return (ComumUser) repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public ComumUser update(ComumUser existingUser, ComumUserUpdateDTO dto) {

        if (dto.name() != null) {
            if (dto.name().isBlank()) {
                throw new ManualValidationException("name", "Name cannot be empty");
            }
            existingUser.setName(dto.name());
        }

        if (dto.password() != null) {
            if (dto.password().isBlank()) {
                throw new ManualValidationException("password", "Password cannot be empty");
            }
            existingUser.setPassword(dto.password());
        }

        if (dto.email() != null) {
            existingUser.setEmail(dto.email().isEmpty() ? null : dto.email());
        }

        if (dto.phone() != null) {
            existingUser.setPhone(dto.phone().isEmpty() ? null : dto.phone());
        }

        if (dto.experience() != null) {
            existingUser.setExperience(dto.experience().isEmpty() ? null : dto.experience());
        }

        if (dto.education() != null) {
            existingUser.setEducation(dto.education().isEmpty() ? null : dto.education());
        }

        return repository.save(existingUser);
    }

    public void delete(ComumUser comumUser) {

        repository.delete(comumUser);
        
    }

}
