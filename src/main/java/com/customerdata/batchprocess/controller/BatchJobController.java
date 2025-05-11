package com.customerdata.batchprocess.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchJobController {

    private final JobLauncher jobLauncher;
    private final Job transactionJob;

    @Autowired
    public BatchJobController(JobLauncher jobLauncher, Job transactionJob) {
        this.jobLauncher = jobLauncher;
        this.transactionJob = transactionJob;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/process")
    public ResponseEntity<String> runBatch() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis()) // unique job instance
                    .toJobParameters();

            jobLauncher.run(transactionJob, jobParameters);
            return ResponseEntity.ok("Batch job has been triggered!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Batch job failed: " + e.getMessage());
        }
    }
}
