package com.example.servervagasrest.repository;

import com.example.servervagasrest.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Application findByIdAndCandidateId(Long jobId, Long candidateId);

    List<Application> findByCandidateId(Long userId);

    List<Application> findByJobId(Long jobId);

    Application findByJobIdAndCandidateId(Long id, Long id1);
}


