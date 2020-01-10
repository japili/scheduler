/*
 * Copyright 2020.
 */

package com.exercise.scheduler.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by japili on 06/01/2020.
 */
@Entity
public class Task {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JsonIgnore
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask")
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
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
