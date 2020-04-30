package com.alok.spring.batch.controller;

import com.alok.spring.batch.model.Employee;
import com.alok.spring.batch.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("EmployeeJob")
    Job job;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/load")
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(parameters);

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        if (log.isDebugEnabled()) {
            log.debug("Batch Status: {}", jobExecution.getStatus());
            log.debug("Batch is running...");
        }

        while (jobExecution.isRunning()) {
            if (log.isDebugEnabled())
                log.debug("...");
        }

        return jobExecution.getStatus();
    }

    @GetMapping("")
    List<Employee> get() {
        return employeeRepository.findAll();
    }


}
