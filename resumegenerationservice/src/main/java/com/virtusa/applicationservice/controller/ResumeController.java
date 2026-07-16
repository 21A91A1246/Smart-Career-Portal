package com.virtusa.applicationservice.controller;

import com.virtusa.applicationservice.entity.ResumeGenerationRequest;
import com.virtusa.applicationservice.entity.TailoredResume;
import com.virtusa.applicationservice.service.ResumeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeGeneratorService resumeService;



    @PostMapping("/generate")
    public TailoredResume createCustomResume(@RequestBody ResumeGenerationRequest request) {
        return resumeService.generatedTailoredResume(request);
    }
}

