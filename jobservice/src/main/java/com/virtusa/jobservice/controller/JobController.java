package com.virtusa.jobservice.controller;


import com.virtusa.jobservice.dto.JobRequest;
import com.virtusa.jobservice.dto.JobResponse;
import com.virtusa.jobservice.entity.Job;
import com.virtusa.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse createJob(@RequestBody JobRequest request) {
        return jobService.createJob(request);
    }

    @GetMapping("/{id}")
    public JobResponse getJob(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("/recruiter/{createdBy}")
    public List<Job> getJobsByRecruiter(
            @PathVariable Long createdBy) {
        return jobService.getJobsByRecruiter(createdBy);
    }
    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PutMapping("/{id}")
    public JobResponse updateJob(
            @PathVariable Long id,
            @RequestBody JobRequest request) {
        return jobService.updateJob(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @GetMapping("/company/{companyName}")
    public List<JobResponse> getByCompany(@PathVariable String companyName) {
        return jobService.getJobsByCompany(companyName);
    }

    @GetMapping("/search")
    public List<JobResponse> search(@RequestParam String keyword) {
        return jobService.searchJobs(keyword);
    }
}