package com.example.demo.batch.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@StepScope
@Component
public class UserTasklet implements Tasklet {

	private static Logger logger = LoggerFactory.getLogger(UserTasklet.class);
	@Autowired(required=true)
	UserRepository repo;
	
	@Value("#{jobParameters['userId']}")
	String userId;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

		logger.info("execute() userFind");
		logger.info("params {}",userId);
		User user = repo.findById(userId);
		logger.info("User Name:{}",user.getName());
		return RepeatStatus.FINISHED;
	}
}
