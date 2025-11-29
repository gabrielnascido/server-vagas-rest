package com.example.servervagasrest.controller;

import com.example.servervagasrest.controller.dto.*;
import com.example.servervagasrest.exception.ForbiddenAccessException;
import com.example.servervagasrest.model.*;
import com.example.servervagasrest.repository.ApplicationRepository;
import com.example.servervagasrest.repository.JobsRepository;
import com.example.servervagasrest.repository.UserRepository;
import com.example.servervagasrest.service.ComumUserService;
import com.example.servervagasrest.service.TokenService;
import com.example.servervagasrest.exception.ImmutableFieldException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class ComumUserController {

    private final ComumUserService comumUserService;
    private final ApplicationRepository applicationRepository;
    private final JobsRepository jobsRepository;
    private final UserRepository userRepository;

    public ComumUserController(ComumUserService comumUserService, TokenService tokenService, ApplicationRepository applicationRepository, JobsRepository jobsRepository, UserRepository userRepository) {
        this.comumUserService = comumUserService;
        this.applicationRepository = applicationRepository;
        this.jobsRepository = jobsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody ComumUser user) {

        comumUserService.create(user);

        Map<String, String> response = Map.of("message", "Created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ComumUserResponseDTO> findById(
            @PathVariable("user_id") Long userId,
            @AuthenticationPrincipal User authenticatedUser) {

        ComumUser user = comumUserService.findById(userId);

        if (!authenticatedUser.getId().equals(userId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        return ResponseEntity.ok(ComumUserResponseDTO.fromEntity(user));
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<Void> update(
            @PathVariable("user_id") Long userId,
            @Valid @RequestBody ComumUserUpdateDTO updateData,
            @AuthenticationPrincipal User authenticatedUser){

        ComumUser user = comumUserService.findById(userId);

        if (!authenticatedUser.getId().equals(userId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        if (updateData.username() != null && !updateData.username().equals(user.getUsername())) {
            throw new ImmutableFieldException("O username não pode ser alterado");
        }

        comumUserService.update(user, updateData);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable("user_id") Long userId,
            @AuthenticationPrincipal User authenticatedUser){

        ComumUser user = comumUserService.findById(userId);

        if (!authenticatedUser.getId().equals(userId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        comumUserService.delete(user);

        Map<String, String> response = Map.of("message", "User deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{user_id}/jobs")
    public ResponseEntity<?> getApplicationsByUserId(
            @PathVariable("user_id") Long userId,
            @AuthenticationPrincipal User authenticatedUser) {

        if (!authenticatedUser.getId().equals(userId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        var applications = applicationRepository.findByCandidateId(userId);

        List<ApplicationsByUserResponseDTO> applicationsDto = applications.stream()
                .map(this::mapToDto)
                .toList();

        ApplicationsByUserWrapperDTO applicationsByUserWrapperDTO = new ApplicationsByUserWrapperDTO(applicationsDto);

        return ResponseEntity.ok(applicationsByUserWrapperDTO);
    }

    public ApplicationsByUserResponseDTO mapToDto(Application application) {

        Job job = jobsRepository.findById(application.getJobId()).get();
        User company = userRepository.findById(job.getCompanyUserId()).get();

        return new ApplicationsByUserResponseDTO(
                application.getJobId(),
                job.getTitle(),
                job.getArea().getName(),
                company.getName(),
                job.getDescription(),
                job.getState(),
                job.getCity(),
                job.getSalary(),
                company.getEmail(),
                application.getFeedback()
        );
    }

}
