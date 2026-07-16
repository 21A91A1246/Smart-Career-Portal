package com.virtusa.jobservice.service;

import com.virtusa.jobservice.dto.JobRequest;
import com.virtusa.jobservice.dto.JobResponse;
import com.virtusa.jobservice.entity.Job;
import com.virtusa.jobservice.exception.JobNotFoundException;
import com.virtusa.jobservice.repository.JobRepository;
import com.virtusa.jobservice.service.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.virtusa.jobservice.entity.JobStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job job;
    private JobRequest request;

    @BeforeEach
    void setUp() {

        request = new JobRequest();
        request.setTitle("Java Developer");
        request.setDescription("Spring Boot Developer");
        request.setLocation("Hyderabad");
        request.setEmploymentType("Full-Time");
        request.setSalary(1200000.0);
        request.setCompanyName("Virtusa");
        request.setCreatedBy(1L);
        request.setStatus(ACTIVE);

        job = Job.builder()
                .id(1L)
                .title("Java Developer")
                .description("Spring Boot Developer")
                .location("Hyderabad")
                .employmentType("Full-Time")
                .salary(1200000.0)
                .companyName("Virtusa")
                .createdBy(1L)
                .status(ACTIVE)
                .build();
    }

    @Test
    void testCreateJob() {

        when(jobRepository.save(any(Job.class))).thenReturn(job);

        JobResponse response = jobService.createJob(request);

        assertNotNull(response);
        assertEquals("Java Developer", response.getTitle());
        assertEquals("Virtusa", response.getCompanyName());

        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void testGetJobById() {

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        JobResponse response = jobService.getJobById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(jobRepository).findById(1L);
    }

    @Test
    void testGetJobById_NotFound() {

        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class,
                () -> jobService.getJobById(1L));

        verify(jobRepository).findById(1L);
    }

    @Test
    void testGetAllJobs() {

        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<JobResponse> jobs = jobService.getAllJobs();

        assertEquals(1, jobs.size());
        assertEquals("Java Developer", jobs.get(0).getTitle());

        verify(jobRepository).findAll();
    }

    @Test
    void testUpdateJob() {

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        request.setTitle("Senior Java Developer");

        JobResponse response = jobService.updateJob(1L, request);

        assertEquals("Senior Java Developer", response.getTitle());

        verify(jobRepository).findById(1L);
        verify(jobRepository).save(any(Job.class));
    }

    @Test
    void testUpdateJob_NotFound() {

        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class,
                () -> jobService.updateJob(1L, request));

        verify(jobRepository).findById(1L);
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testDeleteJob() {

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        jobService.deleteJob(1L);

        verify(jobRepository).findById(1L);
        verify(jobRepository).delete(job);
    }

    @Test
    void testDeleteJob_NotFound() {

        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class,
                () -> jobService.deleteJob(1L));

        verify(jobRepository).findById(1L);
        verify(jobRepository, never()).delete(any());
    }

    @Test
    void testGetJobsByCompany() {

        when(jobRepository.findByCompanyName("Virtusa"))
                .thenReturn(List.of(job));

        List<JobResponse> jobs = jobService.getJobsByCompany("Virtusa");

        assertEquals(1, jobs.size());
        assertEquals("Virtusa", jobs.get(0).getCompanyName());

        verify(jobRepository).findByCompanyName("Virtusa");
    }

    @Test
    void testSearchJobs() {

        when(jobRepository.findByTitleContainingIgnoreCase("Java"))
                .thenReturn(List.of(job));

        when(jobRepository.findByLocationContainingIgnoreCase("Java"))
                .thenReturn(List.of());

        List<JobResponse> jobs = jobService.searchJobs("Java");

        assertEquals(1, jobs.size());
        assertEquals("Java Developer", jobs.get(0).getTitle());

        verify(jobRepository).findByTitleContainingIgnoreCase("Java");
        verify(jobRepository).findByLocationContainingIgnoreCase("Java");
    }

    @Test
    void testGetJobsByRecruiter() {

        when(jobRepository.findByCreatedBy(1L))
                .thenReturn(List.of(job));

        List<Job> jobs = jobService.getJobsByRecruiter(1L);

        assertEquals(1, jobs.size());
        assertEquals(1L, jobs.get(0).getCreatedBy());

        verify(jobRepository).findByCreatedBy(1L);
    }
}