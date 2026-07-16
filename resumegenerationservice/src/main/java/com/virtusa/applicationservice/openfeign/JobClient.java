package com.virtusa.applicationservice.openfeign;


import com.virtusa.applicationservice.entity.JobResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "JobService",url = "http://localhost:8063/api/jobs")
public interface JobClient {
    @GetMapping("/{id}")
    public JobResponse getJob(@PathVariable Long id);
}
