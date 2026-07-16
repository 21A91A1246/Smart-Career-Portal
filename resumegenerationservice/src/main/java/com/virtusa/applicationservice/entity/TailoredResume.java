package com.virtusa.applicationservice.entity;

import java.util.List;

public record TailoredResume(
    String professionalSummary,
    List<String> prioritizedSkills,
    List<TailoredExperience> tailoredExperiences,
    List<TailoredProject> tailoredProjects
) {}


