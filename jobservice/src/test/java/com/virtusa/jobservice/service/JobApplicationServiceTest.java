package com.virtusa.jobservice.service;

import com.virtusa.jobservice.dto.ApplicationResponse;
import com.virtusa.jobservice.dto.ApplyJobRequest;
import com.virtusa.jobservice.entity.ApplicationStatus;
import com.virtusa.jobservice.entity.JobApplication;
import com.virtusa.jobservice.exception.JobApplicationException;
import com.virtusa.jobservice.exception.JobNotFoundException;
import com.virtusa.jobservice.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository applicationRepository;

    @InjectMocks
    private JobApplicationService applicationService;

    private ApplyJobRequest request;
    private JobApplication application;

    @BeforeEach
    void setUp() {

        request = new ApplyJobRequest();
        request.setJobId(1L);
        request.setUserId(101L);
        request.setResumeUrl("resume.pdf");
        request.setCoverLetter("I am interested.");

        application = JobApplication.builder()
                .id(1L)
                .jobId(1L)
                .userId(101L)
                .resumeUrl("resume.pdf")
                .coverLetter("I am interested.")
                .status(ApplicationStatus.APPLIED)
                .build();
    }

    @Test
    void testApplyForJob() {

        when(applicationRepository.findByJobIdAndUserId(1L,101L))
                .thenReturn(Optional.empty());

        when(applicationRepository.save(any(JobApplication.class)))
                .thenReturn(application);

        ApplicationResponse response =
                applicationService.applyForJob(request);

        assertNotNull(response);
        assertEquals(1L,response.getJobId());
        assertEquals(101L,response.getUserId());
        assertEquals(ApplicationStatus.APPLIED,response.getStatus());

        verify(applicationRepository).save(any(JobApplication.class));
    }

    @Test
    void testApplyForJob_AlreadyApplied() {

        when(applicationRepository.findByJobIdAndUserId(1L,101L))
                .thenReturn(Optional.of(application));

        assertThrows(JobApplicationException.class,
                ()->applicationService.applyForJob(request));

        verify(applicationRepository,never()).save(any());
    }

    @Test
    void testGetApplicationsByUser() {

        when(applicationRepository.findByUserId(101L))
                .thenReturn(List.of(application));

        List<ApplicationResponse> responses =
                applicationService.getApplicationsByUser(101L);

        assertEquals(1,responses.size());
        assertEquals(101L,responses.get(0).getUserId());

        verify(applicationRepository).findByUserId(101L);
    }

    @Test
    void testGetApplicationsByJob() {

        when(applicationRepository.findByJobId(1L))
                .thenReturn(List.of(application));

        List<ApplicationResponse> responses =
                applicationService.getApplicationsByJob(1L);

        assertEquals(1,responses.size());
        assertEquals(1L,responses.get(0).getJobId());

        verify(applicationRepository).findByJobId(1L);
    }

    @Test
    void testWithdrawApplication() {

        when(applicationRepository.findById(1L))
                .thenReturn(Optional.of(application));

        applicationService.withdrawApplication(1L);

        verify(applicationRepository).delete(application);
    }

    @Test
    void testWithdrawApplication_NotFound() {

        when(applicationRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class,
                ()->applicationService.withdrawApplication(1L));

        verify(applicationRepository,never()).delete(any());
    }

    @Test
    void testUploadResume() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "resume.pdf",
                        "application/pdf",
                        "resume".getBytes());

        String url = applicationService.uploadResume(file);

        assertNotNull(url);
        assertTrue(url.contains("uploads/resumes"));
    }

    @Test
    void testUploadResume_Exception() {

        MockMultipartFile file =
                mock(MockMultipartFile.class);

        when(file.getOriginalFilename())
                .thenReturn("resume.pdf");

        assertThrows(JobApplicationException.class,
                ()->applicationService.uploadResume(file));
    }
}