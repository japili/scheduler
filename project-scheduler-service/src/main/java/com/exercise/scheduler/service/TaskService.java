/*
 * Copyright 2020
 */

package com.exercise.scheduler.service;

import com.exercise.scheduler.persistence.dao.ProjectRepository;
import com.exercise.scheduler.persistence.dao.TaskRepository;
import com.exercise.scheduler.persistence.model.Project;
import com.exercise.scheduler.persistence.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by japili on 09/01/2020.
 */
@Service
public class TaskService {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<Task> getAvailableTasks(String currentTaskId, String projectId) {
        List<Task> availableTasks = new ArrayList<>();
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project!=null) {
            availableTasks = taskRepository.findByIdNotAndProjectIdAndParentTaskIdIsNull(
                    currentTaskId, projectId);
        }

        return availableTasks;
    }

    public List<Task> getTasks(String projectId) {
        List<Task> tasks = new ArrayList<>();
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project!=null) {
            tasks = taskRepository.findByProjectIdAndParentTaskIdIsNull(projectId);
        }

        return tasks;
    }

    public Task getTask(String id, String projectId) {
        return taskRepository.findByIdAndProjectId(id, projectId);
    }

    public Task addTaskToProject(String projectId, Task task) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Task taskFromDb = new Task();

        if (project!=null) {
            task.setProject(project);
            taskFromDb = taskRepository.save(task);
        }

        return taskFromDb;
    }

    public Task addTaskDependency(String id, String projectId, List<String> taskIds) {
        Task taskToBeUpdated = taskRepository.findByIdAndProjectId(id, projectId);

        if (taskToBeUpdated != null) {
            List<Task> taskList = taskRepository.findByIdIn(taskIds);
            taskToBeUpdated.getTaskDependencies().addAll(taskList);

            for (Task task : taskList) {
                task.setParentTask(taskToBeUpdated);
                taskRepository.save(task);
            }
        }
        return taskRepository.save(taskToBeUpdated);
    }

    public List<Task> generateSchedule(String projectId) {
        List<Task> taskList = getTasks(projectId);

        addDurationToTasks(taskList, LocalDate.now());

        return taskList;
    }

    protected LocalDate addDurationToTasks(List<Task> tasks, LocalDate initialStartDate) {
        ListIterator<Task> taskIterator = tasks.listIterator();
        LocalDate lastEndDate = initialStartDate;

        while(taskIterator.hasNext()) {
            Task previousTask = null;

            if(taskIterator.hasPrevious()) {
                previousTask = taskIterator.previous();
                taskIterator.next();
                lastEndDate = previousTask.getEndDate();
            }

            Task currentTask = taskIterator.next();
            LocalDate startDate = previousTask!=null ? lastEndDate : initialStartDate;

            if(currentTask.getTaskDependencies() != null &&
                    currentTask.getTaskDependencies().size() > 0) {
                lastEndDate = addDurationToTasks(currentTask.getTaskDependencies(), startDate);
            }
            currentTask.setStartDate(lastEndDate);
            lastEndDate = currentTask.getStartDate().plusDays(currentTask.getDuration());
            currentTask.setEndDate(lastEndDate);
        }

        return lastEndDate;
    }

}
