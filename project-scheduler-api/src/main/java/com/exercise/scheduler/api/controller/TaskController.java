/*
 * Copyright 2020
 */

package com.exercise.scheduler.api.controller;

import com.exercise.scheduler.api.resource.TaskResource;
import com.exercise.scheduler.api.util.BeanUtil;
import com.exercise.scheduler.persistence.model.Task;
import com.exercise.scheduler.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by japili on 09/01/2020.
 */
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/projects/{projectId}/task")
    public List<TaskResource> getTasksByProjectId(@PathVariable String projectId) {
        List<Task> tasks = taskService.getTasks(projectId);
        List<TaskResource> taskResources = new ArrayList<>();

        for (Task task : tasks) {
            taskResources.add(BeanUtil.convert(task, TaskResource.class));
        }

        return taskResources;
    }

    @PostMapping("/projects/{projectId}/task")
    public TaskResource addTaskToProject(@PathVariable String projectId, @RequestBody TaskResource
                                         taskResource) {
        Task task = BeanUtil.convert(taskResource, Task.class);
        return BeanUtil.convert(taskService.addTaskToProject(projectId, task), TaskResource.class);
    }

    @GetMapping("/projects/{projectId}/task/{taskId}/dependencies")
    public List<TaskResource> getTaskDependecies(@PathVariable String projectId,
                                                    @PathVariable String taskId) {
        Task task = taskService.getTask(taskId, projectId);
        List<TaskResource> taskResources = new ArrayList<>();

        for (Task taskDependency : task.getTaskDependencies()) {
            taskResources.add(BeanUtil.convert(taskDependency, TaskResource.class));
        }

        return taskResources;
    }

    @GetMapping("/projects/{projectId}/task/{taskId}/availableTasks")
    public List<TaskResource> getAvailableTasks(@PathVariable String projectId,
                                                 @PathVariable String taskId) {
        List<Task> tasks = taskService.getAvailableTasks(taskId, projectId);
        List<TaskResource> taskResources = new ArrayList<>();

        for (Task task : tasks) {
            taskResources.add(BeanUtil.convert(task, TaskResource.class));
        }

        return taskResources;
    }

    @PostMapping("/projects/{projectId}/task/{taskId}/dependencies")
    public TaskResource addTaskDependency(@PathVariable String taskId,
                                                @PathVariable String projectId,
                                                @RequestBody List<String> taskIds) {
        Task updatedTask = taskService.addTaskDependency(taskId, projectId, taskIds);
        return BeanUtil.convert(updatedTask, TaskResource.class);
    }

    @GetMapping("/projects/{projectId}/schedule")
    public List<TaskResource> generateSchedule(@PathVariable String projectId) {
        List<Task> tasksWithScheduleDates = taskService.generateSchedule(projectId);
        List<TaskResource> taskResources = new ArrayList<>();

        for (Task task : tasksWithScheduleDates) {
            taskResources.add(BeanUtil.convert(task, TaskResource.class));
        }

        return taskResources;
    }
}
