package com.virtusa.jobservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobserviceApplication.class, args);
	}

}
