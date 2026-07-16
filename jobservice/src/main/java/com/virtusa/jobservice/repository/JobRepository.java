package com.virtusa.jobservice.repository;


import com.virtusa.jobservice.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompanyName(String companyName);
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findByTitleContainingIgnoreCase(String title);
    List<Job> findByCreatedBy(Long createdBy);
    boolean existsByCreatedByAndTitle(Long createdBy,String title);
}