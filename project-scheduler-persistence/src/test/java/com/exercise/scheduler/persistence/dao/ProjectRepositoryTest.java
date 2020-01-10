/*
 * Copyright 2020
 */

package com.exercise.scheduler.persistence.dao;

import com.exercise.scheduler.persistence.TestDatabaseConfiguration;
import com.exercise.scheduler.persistence.model.Project;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * Created by japili on 06/01/2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfiguration.class })
@Transactional
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void whenSaveProject_thenProjectShouldBePersisted() {
        Project project = new Project();
        project.setId("101");
        project.setName("Test Project");

        projectRepository.save(project);

        Project savedProject = projectRepository.findById("101").orElse(null);
        Assert.assertEquals(savedProject.getName(), project.getName());
    }
}
