/*
 * Copyright 2020
 */

package com.exercise.scheduler.service;

import com.exercise.scheduler.persistence.dao.ProjectRepository;
import com.exercise.scheduler.persistence.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by japili on 07/01/2020.
 */
@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(String projectId) { return projectRepository.getOne(projectId); }

    public Project addProject(String id, String name) {
        Project project = new Project();
        project.setId(id);
        project.setName(name);

        return projectRepository.save(project);
    }

}
