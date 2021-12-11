package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.batch.JobListener;
import com.example.demo.batch.task.UserTasklet;
import com.example.demo.batch.task.WaitTasklet;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private static Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
   
    @Autowired
    private UserTasklet userTask;
    @Autowired
    private WaitTasklet waitTask;

    @Bean
    public Job userJob() {
    	String name = "user";
    	logger.debug("register Job:" + name);
        TaskletStep step = stepBuilderFactory.get("step30").tasklet(userTask).build();
        return jobBuilderFactory.get(name).incrementer(new RunIdIncrementer()).
        		start(step).
        		listener(new JobListener()).build();
    }

    @Bean
    public Job waitJob() {
    	String name = "wait";
    	logger.debug("register Job:" + name);
        TaskletStep step = stepBuilderFactory.get("step60").tasklet(waitTask).build();
        return jobBuilderFactory.get(name).incrementer(new RunIdIncrementer()).
        		start(step).
        		listener(new JobListener()).build();
    }
}
