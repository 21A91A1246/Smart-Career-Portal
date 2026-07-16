package com.virtusa.applicationservice.service;

import com.virtusa.applicationservice.entity.*;
import com.virtusa.applicationservice.exception.AIServiceException;
import com.virtusa.applicationservice.openfeign.JobClient;
import com.virtusa.applicationservice.openfeign.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ResumeGeneratorService {

    private final ChatClient chatClient;
    private final JobClient jobClient;
    private final UserClient userClient;

    // Injecting ChatClient builder configured automatically via starter
    public ResumeGeneratorService(ChatClient.Builder chatClientBuilder, JobClient jobClient, UserClient userClient) {
        this.chatClient = chatClientBuilder.build();
        this.jobClient = jobClient;
        this.userClient = userClient;
    }

//    @CircuitBreaker(name = "resumeService", fallbackMethod = "resumeFallback")
    public TailoredResume generatedTailoredResume(ResumeGenerationRequest request) {
        try {
            JobResponse job = jobClient.getJob(request.jobId());
            System.out.println(job.toString());
            UserProfileResponse profile = userClient.getProfile(request.profileId());
            System.out.println(profile.toString());
            UserResponse user = userClient.getUser(profile.getUserId());
            System.out.println(user.toString());
            String systemPrompt = """
                You are an expert ATS (Applicant Tracking System) optimization coach and recruiter. 
                Your job is to rewrite and restructure the user's profile data into a highly relevant, 
                professional resume tailored specifically to the target Job Description provided.
                
                Guidelines:
                1. Highlight experiences and projects that align closely with the target job requirements.
                2. Rephrase bullet points to emphasize impact, metrics, and targeted keywords.
                3. Do not invent fake details. Only reshape and optimize existing data.
                """;

            String userPrompt = String.format("""
                        Target Job:
                        - Title: %s
                        - Company: %s
                        - Requirements: %s
                        
                        User Profile Data:
                        - Name: %s
                        - Skills: %s
                        - Experiences: %s
                        - Projects: %s
                        """,
                    job.getTitle(), job.getCompanyName(), job.getRequirements(),
                    user.getFirstName() + " " + user.getLastName(), profile.getSkills(), profile.getExperiences(), profile.getProjects()
            );

            // Using Fluent ChatClient API with Structured Output Mapping
            return chatClient.prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .call()
                    .entity(TailoredResume.class); // Spring AI automatically requests & parses JSON to POJO
        }
        catch (Exception ex){
            throw new AIServiceException("AI service failed or token/rate limit issue", ex);
        }
    }

    private TailoredResume resumeFallback(ResumeGenerationRequest request, Throwable ex) {
        throw new AIServiceException(
                "Resume generation service is temporarily unavailable",
                ex
        );
    }
}