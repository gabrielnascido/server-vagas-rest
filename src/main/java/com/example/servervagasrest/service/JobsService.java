package com.example.servervagasrest.service;

import com.example.servervagasrest.config.JobSpecifications;
import com.example.servervagasrest.controller.dto.*;
import com.example.servervagasrest.exception.ForbiddenAccessException;
import com.example.servervagasrest.exception.JobNotFoundException;
import com.example.servervagasrest.model.Application;
import com.example.servervagasrest.model.Job;
import com.example.servervagasrest.model.User;
import com.example.servervagasrest.repository.ApplicationRepository;
import com.example.servervagasrest.repository.JobsRepository;
import com.example.servervagasrest.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsService {

    private final JobsRepository jobsRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public JobsService(JobsRepository jobsRepository, UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.jobsRepository = jobsRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public Job create(Job job) {
        return jobsRepository.save(job);
    }

    public Job findById(Long jobId) {
        return jobsRepository.findById(jobId).
                orElseThrow(() -> new JobNotFoundException("Job not found"));
    }

    public JobsResponseDTO mapToDto(Job job) {
        String companyName = userRepository.findById(job.getCompanyUserId())
                .map(User::getName)
                .orElse("Empresa Desconhecida");

        return new JobsResponseDTO(
                job.getId(),
                job.getTitle(),
                job.getArea().getName(),
                job.getDescription(),
                companyName,
                job.getCity(),
                job.getState(),
                job.getSalary()
        );
    }

    public List<JobsResponseDTO> searchByFilters(List<JobFilterDTO> filters) {
        return searchByFilters(filters, null);
    }

    public List<JobsResponseDTO> searchByFilters(List<JobFilterDTO> filters, Long companyId) {

        Specification<Job> finalSpec = Specification.where(null);

        if (companyId != null) {
            Specification<Job> companySpec = (root, query, builder) ->
                    builder.equal(root.get("companyUserId"), companyId);

            finalSpec = finalSpec.and(companySpec);
        }

        if (filters == null || filters.isEmpty()) {
            List<Job> allJobs = jobsRepository.findAll(finalSpec);
            return allJobs.stream().map(this::mapToDto).toList();
        }

        Specification<Job> optionalFiltersSpec = Specification.where(null);

        for (JobFilterDTO filter : filters) {

            SalaryRangeDTO salaryRange = filter.salary_range();

            Specification<Job> currentFilterSpec = JobSpecifications.hasTitleContaining(filter.title())
                    .and(JobSpecifications.hasAreaContaining(filter.area()))
                    .and(JobSpecifications.hasCity(filter.city()))
                    .and(JobSpecifications.hasState(filter.state()))
                    .and(JobSpecifications.hasCompanyNameContaining(filter.company()))

                    .and(JobSpecifications.isSalaryBetween(
                            salaryRange != null ? salaryRange.min() : null,
                            salaryRange != null ? salaryRange.max() : null
                    ));

            optionalFiltersSpec = optionalFiltersSpec.or(currentFilterSpec);
        }
        finalSpec = finalSpec.and(optionalFiltersSpec);


        List<Job> jobEntities = jobsRepository.findAll(finalSpec);

        return jobEntities.stream()
                .map(this::mapToDto)
                .toList();
    }

    public Job update(Job existingJob, JobUpdateDTO dto) {

        if (dto.title() != null) {
            existingJob.setTitle(dto.title());
        }

        if (dto.description() != null) {
            existingJob.setDescription(dto.description());
        }

        if (dto.city() != null) {
            existingJob.setCity(dto.city());
        }

        if (dto.state() != null) {
            existingJob.setState(dto.state());
        }

        if (dto.salary() != null) {
            existingJob.setSalary(dto.salary());
        }

        return jobsRepository.save(existingJob);
    }

    public void delete(Job job) {
        jobsRepository.delete(job);
    }

    public void applyToJob(Job job, User candidate) {

        if (candidate.getRole().equals("company") ) {
            throw new ForbiddenAccessException("Only comum users can apply to jobs");
        }

        Application application = new Application();
        application.setJobId(job.getId());
        application.setCandidateId(candidate.getId());

        applicationRepository.save(application);
    }

    public void giveFeedback(Job job, User candidate, String feedback) {
        Application application = applicationRepository.findByJobIdAndCandidateId(job.getId(), candidate.getId());

        if (application == null) {
            throw new ForbiddenAccessException("Job, User or Application not found");
        }

        application.setFeedback(feedback);

        applicationRepository.save(application);
    }

}