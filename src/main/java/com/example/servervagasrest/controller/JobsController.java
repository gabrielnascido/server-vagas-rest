package com.example.servervagasrest.controller;

import com.example.servervagasrest.controller.dto.*;
import com.example.servervagasrest.model.Job;
import com.example.servervagasrest.model.User;
import com.example.servervagasrest.repository.UserRepository;
import com.example.servervagasrest.service.ComumUserService;
import com.example.servervagasrest.service.JobsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobsController {

    private final JobsService jobsService;
    private final ComumUserService comumUserService;

    public JobsController(JobsService jobsService, UserRepository userRepository, ComumUserService comumUserService) {
        this.jobsService = jobsService;
        this.comumUserService = comumUserService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody Job job,
                                                      @AuthenticationPrincipal User user) {

        Long companyId = user.getId();

        job.setCompanyUserId(companyId);
        jobsService.create(job);

        Map<String, String> response = Map.of("message", "Created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{job_id}")
    public ResponseEntity<JobsResponseDTO> findById( @PathVariable("job_id") Long jobId) {

        Job job = jobsService.findById(jobId);

        JobsResponseDTO jobDto = jobsService.mapToDto(job);

        return ResponseEntity.ok(jobDto);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchJobs(
            @RequestBody SearchRequestDTO searchRequest,
            @AuthenticationPrincipal User authenticatedUser) {

        List<JobFilterDTO> filters = searchRequest.filters();

        List<JobsResponseDTO> jobs = jobsService.searchByFilters(filters);

        if (jobs.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Jobs not found"), HttpStatus.NOT_FOUND);
        }

        SearchResponseWrapperDTO responseWrapper = new SearchResponseWrapperDTO(jobs);

        return ResponseEntity.ok(responseWrapper);
    }

    @PatchMapping("/{job_id}")
    public ResponseEntity<?> update(
            @PathVariable("job_id") Long jobId,
            @Valid @RequestBody JobUpdateDTO updateData,
            @AuthenticationPrincipal User authenticatedUser) {

        Job job = jobsService.findById(jobId);


        if (job == null) {
            return new ResponseEntity<>(Map.of("message", "Job not found"), HttpStatus.NOT_FOUND);
        }

        if (!authenticatedUser.getId().equals(job.getCompanyUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        jobsService.update(job, updateData);

        Map<String, String> response = Map.of("message", "Job updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/{job_id}")
    public ResponseEntity<?> delete(
            @PathVariable("job_id") Long jobId,
            @AuthenticationPrincipal User authenticatedUser) {

        Job job = jobsService.findById(jobId);

        if (job == null) {
            return new ResponseEntity<>(Map.of("message", "Job not found"), HttpStatus.NOT_FOUND);
        }

        if (!authenticatedUser.getId().equals(job.getCompanyUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        jobsService.delete(job);

        Map<String, String> response = Map.of("message", "Job deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //aplicar a vaga
    @PostMapping("/{job_id}")
    public ResponseEntity<?> applyToJob(
            @PathVariable("job_id") Long jobId,
            @AuthenticationPrincipal User authenticatedUser) {

        Job job = jobsService.findById(jobId);

        if (job == null) {
            return new ResponseEntity<>(Map.of("message", "Job not found"), HttpStatus.NOT_FOUND);
        }

        jobsService.applyToJob(job, authenticatedUser);

        Map<String, String> response = Map.of("message", "Applied successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //dar feedback da vaga
    @PostMapping("/{job_id}/feedback")
    public ResponseEntity<?> giveFeedback(
            @PathVariable("job_id") Long jobId,
            @Valid @RequestBody FeedbackDTO feedbackDTO,
            @AuthenticationPrincipal User authenticatedUser) {

        Job job = jobsService.findById(jobId);
        User user = comumUserService.findById(feedbackDTO.user_id());

        if (job == null) {
            return new ResponseEntity<>(Map.of("message", "Job not found"), HttpStatus.NOT_FOUND);
        }

        if(!authenticatedUser.getId().equals(job.getCompanyUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        jobsService.giveFeedback(job, user, feedbackDTO.getMessage());

        Map<String, String> response = Map.of("message", "Feedback sent successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
