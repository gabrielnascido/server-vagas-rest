package com.example.servervagasrest.repository;

import com.example.servervagasrest.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    List<Job> findAllByCompanyUserId(Long companyUserId);
    List<Job> findByCompanyUserId(Long companyUserId);
}
