package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Bean(name="validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    @Bean(name="messageSource")
    public ReloadableResourceBundleMessageSource messageSource() { 
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource(); 
        messageSource.setBasename("classpath:locale/resource"); 
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource; 
    }
}
