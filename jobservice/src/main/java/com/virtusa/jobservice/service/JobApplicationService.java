package com.virtusa.jobservice.service;

import com.virtusa.jobservice.dto.ApplicationResponse;
import com.virtusa.jobservice.dto.ApplyJobRequest;
import com.virtusa.jobservice.entity.ApplicationStatus;
import com.virtusa.jobservice.entity.JobApplication;
import com.virtusa.jobservice.exception.JobApplicationException;
import com.virtusa.jobservice.exception.JobNotFoundException;
import com.virtusa.jobservice.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;

    public ApplicationResponse applyForJob(
            ApplyJobRequest request) {

        if (applicationRepository
                .findByJobIdAndUserId(
                        request.getJobId(),
                        request.getUserId())
                .isPresent()) {

            throw new JobApplicationException("Already applied for this job");
        }

        JobApplication application =
                JobApplication.builder()
                        .jobId(request.getJobId())
                        .userId(request.getUserId())
                        .resumeUrl(request.getResumeUrl())
                        .coverLetter(request.getCoverLetter())
                        .status(ApplicationStatus.APPLIED)
                        .appliedAt(LocalDateTime.now())
                        .build();

        return mapToResponse(
                applicationRepository.save(application));
    }

    public List<ApplicationResponse> getApplicationsByUser(
            Long userId) {

        return applicationRepository
                .findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ApplicationResponse> getApplicationsByJob(
            Long jobId) {

        return applicationRepository
                .findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void withdrawApplication(
            Long applicationId) {

        JobApplication application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new JobNotFoundException("Application not found"));
        applicationRepository.delete(application);
    }

    private ApplicationResponse mapToResponse( JobApplication application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJobId())
                .userId(application.getUserId())
                .resumeUrl(application.getResumeUrl())
                .coverLetter(application.getCoverLetter())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }

    public String uploadResume(MultipartFile file) {
        try {
            String uploadDir = "uploads/resumes/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            Files.copy(
                    file.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return "http://localhost:8063/uploads/resumes/" + fileName;
        } catch (Exception e) {
            throw new JobApplicationException("Resume upload failed   \n " +
                    "  "+e.getMessage());
        }
    }
}