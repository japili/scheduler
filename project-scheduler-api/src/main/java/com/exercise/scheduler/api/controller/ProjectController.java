/*
 * Copyright 2020
 */

package com.exercise.scheduler.api.controller;

import com.exercise.scheduler.api.resource.ProjectResource;
import com.exercise.scheduler.api.util.BeanUtil;
import com.exercise.scheduler.persistence.model.Project;
import com.exercise.scheduler.persistence.model.Task;
import com.exercise.scheduler.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by japili on 07/01/2020.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping
    public List<ProjectResource> getProjects() {
        List<Project> projects = projectService.getProjects();
        List<ProjectResource> projectResources = new ArrayList<>();

        for (Project project : projects) {
            projectResources.add(BeanUtil.convert(project, ProjectResource.class));
        }

        return projectResources;
    }

    @PostMapping
    public ProjectResource addProject(@RequestBody ProjectResource projectResource) {
        return BeanUtil.convert(
                projectService.addProject(projectResource.getId(), projectResource.getName()),
                    ProjectResource.class);
    }

    @GetMapping("/{projectId}")
    public ProjectResource getProject(@PathVariable String projectId) {
        Project project = projectService.getProject(projectId);

        return BeanUtil.convert(project, ProjectResource.class);
    }
}
