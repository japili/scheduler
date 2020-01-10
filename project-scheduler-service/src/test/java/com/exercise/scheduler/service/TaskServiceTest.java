/*
 * Copyright 2020
 */

package com.exercise.scheduler.service;

import com.exercise.scheduler.persistence.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by japili on 10/01/2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    private Task firstTask;
    private Task dependencyTask1;
    private Task dependencyTask2;

    private Task dependencyTask3;

    private Task secondTask;

    private Task thirdTask;
    private Task dependencyTask4;

    @Before
    public void setupObjects() {
        firstTask = new Task();
        firstTask.setId("1");
        firstTask.setName("Parent Task");
        firstTask.setDuration(6);

        dependencyTask1 = new Task();
        dependencyTask1.setId("11");
        dependencyTask1.setName("Sub Task 1");
        dependencyTask1.setParentTask(firstTask);
        dependencyTask1.setDuration(4);

        dependencyTask3 = new Task();
        dependencyTask3.setId("111");
        dependencyTask3.setName("Sub Task of Sub Task 1");
        dependencyTask3.setParentTask(dependencyTask1);
        dependencyTask3.setDuration(5);

        dependencyTask1.setTaskDependencies(new ArrayList<>(Arrays.asList(dependencyTask3)));

        dependencyTask2 = new Task();
        dependencyTask2.setId("22");
        dependencyTask2.setName("Sub Task 2");
        dependencyTask2.setParentTask(firstTask);
        dependencyTask2.setDuration(3);

        firstTask.setTaskDependencies(new ArrayList<>(Arrays.asList(dependencyTask1, dependencyTask2)));

        secondTask = new Task();
        secondTask.setId("2");
        secondTask.setName("Second Task");
        secondTask.setDuration(9);

        thirdTask = new Task();
        thirdTask.setId("3");
        thirdTask.setName("Third Task");
        thirdTask.setDuration(2);

        dependencyTask4 = new Task();
        dependencyTask4.setId("333");
        dependencyTask4.setName("Sub Task of Third Task");
        dependencyTask4.setParentTask(thirdTask);
        dependencyTask4.setDuration(1);

        thirdTask.setTaskDependencies(new ArrayList<>(Arrays.asList(dependencyTask4)));
    }

    @Test
    public void testAddDurationToTask() {
        LocalDate endDate = taskService.addDurationToTasks(
                new ArrayList<>(Arrays.asList(secondTask, firstTask, thirdTask)),
                LocalDate.parse("2020-01-01"));

        //total days added should be 30
        Assert.assertTrue(endDate.isEqual(LocalDate.parse("2020-01-31")));
    }
}
