package com.customerdata.batchprocess.runner;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public void run() throws Exception {
        jobLauncher.run(job, new JobParameters());
    }
}
