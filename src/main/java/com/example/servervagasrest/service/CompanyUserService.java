package com.example.servervagasrest.service;

import com.example.servervagasrest.controller.dto.CompanyUserUpdateDTO;
import com.example.servervagasrest.exception.UserNotFoundException;
import com.example.servervagasrest.model.CompanyUser;
import com.example.servervagasrest.repository.UserRepository;
import com.example.servervagasrest.exception.UsernameConflictException;
import org.springframework.stereotype.Service;

@Service
public class CompanyUserService {

    private final UserRepository repository;

    public  CompanyUserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public CompanyUser create (CompanyUser companyUser) {

        if(repository.existsByUsername(companyUser.getUsername())) {
            throw new UsernameConflictException("Username already exists");
        }

        return repository.save(companyUser);

    }

    public CompanyUser findById(Long id) {
        return (CompanyUser) repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void update(CompanyUser existingUser, CompanyUserUpdateDTO dto) {

        if (dto.name() != null) {
            existingUser.setName(dto.name());
        }

        if (dto.username() != null) {
            existingUser.setUsername(dto.username());
        }

        if (dto.password() != null) {
            existingUser.setPassword(dto.password());
        }

        if (dto.business() != null) {
            existingUser.setBusiness(dto.business());
        }

        if (dto.street() != null) {
            existingUser.setStreet(dto.street());
        }

        if (dto.city() != null) {
            existingUser.setCity(dto.city());
        }

        if (dto.number() != null) {
            existingUser.setNumber(dto.number());
        }

        if (dto.state() != null) {
            existingUser.setState(dto.state());
        }

        if (dto.phone() != null) {
            existingUser.setPhone(dto.phone());
        }

        if (dto.email() != null) {
            existingUser.setEmail(dto.email());
        }

        repository.save(existingUser);
    }

    public void delete(CompanyUser companyUser) {

        repository.delete(companyUser);

    }

}
