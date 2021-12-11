package com.example.demo.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {
	
	private static Logger logger = LoggerFactory.getLogger(JobListener.class);
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Job Start");
	}
	@Override
	public void afterJob(JobExecution jobExecution) {
		if ( jobExecution.getStatus().isUnsuccessful() ) {
		} else {
			logger.info("Job Success");
		}
	}

}
