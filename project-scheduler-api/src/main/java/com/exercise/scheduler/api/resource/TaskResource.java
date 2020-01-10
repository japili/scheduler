/*
 * Copyright 2020
 */

package com.exercise.scheduler.api.resource;

import com.exercise.scheduler.persistence.model.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by japili on 09/01/2020.
 */
public class TaskResource {
    private String id;

    private String name;

    private Integer duration;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Task> taskDependencies;

    @JsonFormat(pattern = "MMM-dd-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "MMM-dd-yyyy")
    private LocalDate endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Task> getTaskDependencies() {
        return taskDependencies;
    }

    public void setTaskDependencies(List<Task> taskDependencies) {
        this.taskDependencies = taskDependencies;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
