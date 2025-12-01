package com.example.servervagasrest.controller;

import com.example.servervagasrest.controller.dto.*;
import com.example.servervagasrest.exception.ForbiddenAccessException;
import com.example.servervagasrest.model.*;
import com.example.servervagasrest.repository.ApplicationRepository;
import com.example.servervagasrest.repository.JobsRepository;
import com.example.servervagasrest.repository.UserRepository;
import com.example.servervagasrest.service.CompanyUserService;
import com.example.servervagasrest.service.JobsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.servervagasrest.exception.ImmutableFieldException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyUserController {

    private final CompanyUserService companyUserService;
    private final JobsService jobsService;
    private final ApplicationRepository applicationRepository;
    private final JobsRepository jobsRepository;
    private final UserRepository userRepository;

    public CompanyUserController(CompanyUserService companyUserService, JobsService jobsService, ApplicationRepository applicationRepository, JobsRepository jobsRepository, UserRepository userRepository) {
        this.companyUserService = companyUserService;
        this.jobsService = jobsService;
        this.applicationRepository = applicationRepository;
        this.jobsRepository = jobsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody CompanyUser user) {

        companyUserService.create(user);

        Map<String, String> response = Map.of("message", "Created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{company_id}")
    public ResponseEntity<CompanyUserResponseDTO> findById(
            @PathVariable("company_id") Long companyId,
            @AuthenticationPrincipal User authenticatedUser) {

        CompanyUser user = companyUserService.findById(companyId);

        if (!authenticatedUser.getId().equals(companyId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        return ResponseEntity.ok(CompanyUserResponseDTO.fromEntity(user));
    }

    @PatchMapping("/{company_id}")
    public ResponseEntity<Void> update(
            @PathVariable("company_id") Long companyId,
            @Valid @RequestBody CompanyUserUpdateDTO updateData,
            @AuthenticationPrincipal User authenticatedUser){

        CompanyUser user = companyUserService.findById(companyId);

        if (!authenticatedUser.getId().equals(companyId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        if (updateData.username() != null && !updateData.username().equals(user.getUsername())) {
            throw new ImmutableFieldException("O username não pode ser alterado");
        }

        companyUserService.update(user, updateData);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{company_id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable("company_id") Long companyId,
            @AuthenticationPrincipal User authenticatedUser){

        CompanyUser user = companyUserService.findById(companyId);

        if (user == null) {
            return new ResponseEntity<>(Map.of("message", "Company not found"), HttpStatus.NOT_FOUND);
        }

        //se houver uma vaga, a empresa não pode ser deletada
        List<Job> jobs = jobsRepository.findByCompanyUserId(companyId);

        if (!jobs.isEmpty()) {
            throw new ForbiddenAccessException("Cannot delete company with existing job postings");
        }

        if (!authenticatedUser.getId().equals(companyId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        companyUserService.delete(user);

        Map<String, String> response = Map.of("message", "Company deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{company_id}/jobs")
    public ResponseEntity<SearchResponseWrapperDTO> searchCompanyJobs(
            @PathVariable("company_id") Long companyId,
            @RequestBody SearchRequestDTO searchRequest,
            @AuthenticationPrincipal User authenticatedUser) {

        if (!authenticatedUser.getId().equals(companyId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        List<JobsResponseDTO> jobs = jobsService.searchByFilters(searchRequest.filters(), companyId);

        return ResponseEntity.ok(new SearchResponseWrapperDTO(jobs));
    }


    //listar candidatos de uma vaga
    @GetMapping("{company_id}/jobs/{job_id}")
    public ResponseEntity<?> getApplicationsByJobId(
            @PathVariable("company_id") Long companyId,
            @PathVariable("job_id") Long jobId,
            @AuthenticationPrincipal User authenticatedUser) {

        if (!authenticatedUser.getId().equals(companyId)) {
            throw new ForbiddenAccessException("Forbidden");
        }

        var applications = applicationRepository.findByJobId(jobId);

        List<JobCandidatesResponseDTO> applicationsDto = applications.stream()
                .map(this::mapToDto)
                .toList();

        JobCandidatesWrapperResponseDTO applicationsByJobWrapperDTO = new JobCandidatesWrapperResponseDTO(applicationsDto);

        return ResponseEntity.ok(applicationsByJobWrapperDTO);
    }

    public JobCandidatesResponseDTO mapToDto(Application application) {

        Job job = jobsRepository.findById(application.getJobId()).get();
        ComumUser candidate = (ComumUser) userRepository.findById(application.getCandidateId()).get();

        return new JobCandidatesResponseDTO(
                application.getCandidateId(),
                candidate.getName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getEducation(),
                candidate.getExperience()
        );
    }
}
