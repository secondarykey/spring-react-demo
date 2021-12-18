package com.example.demo.batch.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class WaitTasklet implements Tasklet {

	private static Logger logger = LoggerFactory.getLogger(WaitTasklet.class);
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		logger.info("execute() wait");

		try {
			Thread.sleep(60 * 1000); 
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep失敗",e);
		}
		
		return RepeatStatus.FINISHED;
	}
}
