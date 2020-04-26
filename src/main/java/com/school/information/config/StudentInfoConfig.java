package com.school.information.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class StudentInfoConfig {

    @Bean(name = "dozerMapper")
    public DozerBeanMapperFactoryBean dozerMapper() throws IOException {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        final Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*mappings.xml");
        mapper.setMappingFiles(resources);
        return mapper;
    }
}
