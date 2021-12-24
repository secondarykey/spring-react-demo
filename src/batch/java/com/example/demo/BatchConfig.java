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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.example.demo.batch.JobListener;
import com.example.demo.batch.task.ResourceTasklet;
import com.example.demo.batch.task.UserTasklet;
import com.example.demo.batch.task.WaitTasklet;

import com.example.demo.config.Session;
import com.example.demo.controller.AuthenticationInterceptor;

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
    @Autowired
    private ResourceTasklet resourceTask;

    @Bean
    public Job userJob() {
    	String name = "user";
    	logger.debug("register Job:" + name);
        TaskletStep step = stepBuilderFactory.get(name).tasklet(userTask).build();
        return jobBuilderFactory.get(name).incrementer(new RunIdIncrementer()).
        		start(step).
        		listener(new JobListener()).build();
    }

    @Bean
    public Job waitJob() {
    	String name = "wait";
    	logger.debug("register Job:" + name);
        TaskletStep step = stepBuilderFactory.get(name).tasklet(waitTask).build();
        return jobBuilderFactory.get(name).incrementer(new RunIdIncrementer()).
        		start(step).
        		listener(new JobListener()).build();
    }
    @Bean
    public Job resouceJob() {
    	String name = "createResource";
    	logger.debug("register Job:" + name);
        TaskletStep step = stepBuilderFactory.get(name).tasklet(resourceTask).build();
        return jobBuilderFactory.get(name).incrementer(new RunIdIncrementer()).
        		start(step).
        		listener(new JobListener()).build();
    }
}
