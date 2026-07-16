package com.virtusa.applicationservice.entity;

import java.util.List;

public record TailoredExperience(String company, String role, String duration, List<String> bulletPoints) {}