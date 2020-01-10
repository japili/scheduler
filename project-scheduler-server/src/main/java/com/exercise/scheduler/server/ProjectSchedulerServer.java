/*
 * Copyright 2020
 */

package com.exercise.scheduler.server;

import com.exercise.scheduler.api.controller.ProjectController;
import com.exercise.scheduler.api.controller.TaskController;
import com.exercise.scheduler.persistence.dao.ProjectRepository;
import com.exercise.scheduler.persistence.dao.TaskRepository;
import com.exercise.scheduler.persistence.model.Project;
import com.exercise.scheduler.persistence.model.Task;
import com.exercise.scheduler.service.ProjectService;
import com.exercise.scheduler.service.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by japili on 07/01/2020.
 */
@ComponentScan(basePackageClasses = {
    ProjectController.class,
    TaskController.class,
})

@ComponentScan(basePackageClasses = {
    ProjectService.class,
    TaskService.class
})

@EnableJpaRepositories(basePackageClasses = {
    ProjectRepository.class,
    TaskRepository.class
})

@EntityScan(basePackageClasses = {
    Project.class,
    Task.class
})

@SpringBootApplication
public class ProjectSchedulerServer {

    public static void main(String[] args) {
        SpringApplication.run(ProjectSchedulerServer.class, args);
    }
}
