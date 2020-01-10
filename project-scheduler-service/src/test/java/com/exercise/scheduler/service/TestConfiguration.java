/*
 * Copyright 2020
 */

package com.exercise.scheduler.service;

/**
 * Created by japili on 10/01/2020.
 */

import com.exercise.scheduler.persistence.dao.ProjectRepository;
import com.exercise.scheduler.persistence.dao.TaskRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation
    .ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.exercise.scheduler.service")
public class TestConfiguration {

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private TaskRepository taskRepository;
}
