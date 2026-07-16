package com.virtusa.jobservice.service.impl;

import com.virtusa.jobservice.dto.JobRequest;
import com.virtusa.jobservice.dto.JobResponse;
import com.virtusa.jobservice.entity.Job;
import com.virtusa.jobservice.exception.JobApplicationException;
import com.virtusa.jobservice.exception.JobNotFoundException;
import com.virtusa.jobservice.repository.JobRepository;
import com.virtusa.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponse createJob(JobRequest request) {

        if(jobRepository.existsByCreatedByAndTitle(request.getCreatedBy(), request.getTitle())){
            throw new JobApplicationException("Duplicate Job found with "+request.getCreatedBy()+"and title"+request.getTitle());
        }

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .employmentType(request.getEmploymentType())
                .salary(request.getSalary())
                .companyName(request.getCompanyName())
                .createdBy(request.getCreatedBy())
                .status(request.getStatus())
                .build();

        return mapToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException("Job not found with the id: " + id));
        return mapToResponse(job);
    }

    @Override
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobResponse updateJob(Long id, JobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found with id: " + id));
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setSalary(request.getSalary());
        job.setStatus(request.getStatus());

        return mapToResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
        jobRepository.delete(job);
    }

    @Override
    public List<JobResponse> getJobsByCompany(String companyName) {

        return jobRepository.findByCompanyName(companyName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<JobResponse> searchJobs(String keyword) {
        List<Job> byTitle = jobRepository
                .findByTitleContainingIgnoreCase(keyword);

        List<Job> byLocation = jobRepository
                .findByLocationContainingIgnoreCase(keyword);

        return List.of(byTitle, byLocation)
                .stream()
                .flatMap(List::stream)
                .distinct()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<Job> getJobsByRecruiter(Long createdBy) {
        return jobRepository.findByCreatedBy(createdBy);
    }

    private JobResponse mapToResponse(Job job) {

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .employmentType(job.getEmploymentType())
                .salary(job.getSalary())
                .companyName(job.getCompanyName())
                .createdBy(job.getCreatedBy())
                .status(job.getStatus())
                .build();
    }
}