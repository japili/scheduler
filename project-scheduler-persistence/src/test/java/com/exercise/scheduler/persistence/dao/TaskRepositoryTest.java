/*
 * Copyright 2020
 */

package com.exercise.scheduler.persistence.dao;

import com.exercise.scheduler.persistence.TestDatabaseConfiguration;
import com.exercise.scheduler.persistence.model.Project;
import com.exercise.scheduler.persistence.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by japili on 08/01/2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfiguration.class })
@Transactional
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;

    private Task firstTask;
    private Task dependencyTask1;
    private Task dependencyTask2;

    private Task availableTask;

    private Task taskWithDependency;
    private Task dependencyTask3;

    @Before
    public void setupObjects() {
        project = new Project();
        project.setId("P1");
        project.setName("First Project");
        projectRepository.save(project);

        firstTask = new Task();
        firstTask.setId("1");
        firstTask.setName("Parent Task");
        firstTask.setProject(project);
        taskRepository.save(firstTask);

        dependencyTask1 = new Task();
        dependencyTask1.setId("11");
        dependencyTask1.setName("Sub Task 1");
        dependencyTask1.setParentTask(firstTask);
        dependencyTask1.setProject(project);
        taskRepository.save(dependencyTask1);

        dependencyTask2 = new Task();
        dependencyTask2.setId("22");
        dependencyTask2.setName("Sub Task 2");
        dependencyTask2.setParentTask(firstTask);
        dependencyTask2.setProject(project);
        taskRepository.save(dependencyTask2);

        firstTask.setTaskDependencies(new ArrayList<>(Arrays.asList(dependencyTask1, dependencyTask2)));
        taskRepository.save(firstTask);

        availableTask = new Task();
        availableTask.setId("33");
        availableTask.setProject(project);
        availableTask.setName("Unassigned Task");
        taskRepository.save(availableTask);
//
//        taskWithDependency = new Task();
//        taskWithDependency.setId("44");
//        taskWithDependency.setProject(project);
//        taskWithDependency.setName("Task with dependency");
//        taskRepository.save(taskWithDependency);
//
//        dependencyTask3 = new Task();
//        dependencyTask3.setId("333");
//        dependencyTask3.setName("Sub Task 3 for task with dependency");
//        dependencyTask3.setParentTask(taskWithDependency);
//        dependencyTask3.setProject(project);
//        taskRepository.save(dependencyTask3);
    }

    @Test
    public void whenSubTaskIsSaved_thenGetParentTaskShouldReturnSubtasks() {
        Task taskFromDb = taskRepository.findById("1").orElse(null);
        Assert.assertEquals(taskFromDb.getTaskDependencies().size(), 2);
    }

    @Test
    public void whenAvailableTaskIsQueried_thenReturnAvailableTasks() {
        //should not show first task
        List<Task> tasksFromDb = taskRepository
            .findByIdNotAndProjectIdAndParentTaskIdIsNull("1", "P1");
        Assert.assertEquals(tasksFromDb.size(), 1);
    }

    @Test
    public void whenAvailableTaskIsAdded_thenNumberOfAvailableTasksShouldBeUpdated() {
        List<Task> tasksFromDb = taskRepository
            .findByIdNotAndProjectIdAndParentTaskIdIsNull("1", "P1");
        Task taskFromDb = tasksFromDb.get(0);

        taskFromDb.setParentTask(firstTask);
        List<Task> taskDependecies = firstTask.getTaskDependencies();
        taskDependecies.add(availableTask);

        taskRepository.save(firstTask);
        taskRepository.save(taskFromDb);

        List<Task> updatedTasksFromDb = taskRepository
            .findByIdNotAndProjectIdAndParentTaskIdIsNull("1", "P1");
        Assert.assertEquals(updatedTasksFromDb.size(), 0);
    }

    @Test
    public void whenTaskIsAddedToProject_thenFindByIdAndProjectIdShouldReturnNotNull() {
        Task taskFromDb = taskRepository.findByIdAndProjectId("11", "P1");
        Assert.assertEquals(taskFromDb.getName(), "Sub Task 1");
    }

    @Test
    public void whenTaskIsAddedToProject_thenFindByProjectIdShouldReturnNotNull() {
        List<Task> tasksFromDb = taskRepository.findByProjectIdAndParentTaskIdIsNull("P1");
        Assert.assertEquals(tasksFromDb.size(), 2);
    }

    @Test
    public void whenFindByTasksIds_thenReturnTaskList() {
        List<Task> tasks = taskRepository.findByIdIn(new ArrayList<>(Arrays.asList("11", "22",
            "33")));

        Assert.assertEquals(tasks.size(), 3);
    }
}