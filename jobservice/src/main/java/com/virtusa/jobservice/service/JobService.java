package com.virtusa.jobservice.service;



import com.virtusa.jobservice.dto.JobRequest;
import com.virtusa.jobservice.dto.JobResponse;
import com.virtusa.jobservice.entity.Job;

import java.util.List;

public interface JobService {

    JobResponse createJob(JobRequest request);

    JobResponse getJobById(Long id);

    List<JobResponse> getAllJobs();

    JobResponse updateJob(Long id, JobRequest request);

    void deleteJob(Long id);

    List<JobResponse> getJobsByCompany(String companyName);

    List<JobResponse> searchJobs(String keyword);

    List<Job> getJobsByRecruiter(Long createdBy);
}