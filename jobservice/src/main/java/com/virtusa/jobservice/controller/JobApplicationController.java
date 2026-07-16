package com.virtusa.jobservice.controller;

import com.virtusa.jobservice.dto.ApplicationResponse;
import com.virtusa.jobservice.dto.ApplyJobRequest;
import com.virtusa.jobservice.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService applicationService;

    @PostMapping("/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse apply( @Valid @RequestBody ApplyJobRequest request) {
        return applicationService.applyForJob(request);
    }

    @GetMapping("/user/{userId}")
    public List<ApplicationResponse> getByUser(@PathVariable Long userId) {
        return applicationService.getApplicationsByUser(userId);
    }

    @GetMapping("/job/{jobId}")
    public List<ApplicationResponse> getByJob(@PathVariable Long jobId) {
        return applicationService
                .getApplicationsByJob(jobId);
    }

    @DeleteMapping("/deleteApplication/{applicationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable Long applicationId) {
        applicationService
                .withdrawApplication(applicationId);
    }
    @PostMapping("/upload-resume")
    public String uploadResume(@RequestParam("file") MultipartFile file) {
        return applicationService.uploadResume(file);
    }
}