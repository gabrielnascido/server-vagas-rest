package com.example.servervagasrest.repository;

import com.example.servervagasrest.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
}
