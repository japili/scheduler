/*
 * Copyright 2020
 */

package com.exercise.scheduler.persistence.dao;

import com.exercise.scheduler.persistence.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by japili on 06/01/2020.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByIdNotAndProjectIdAndParentTaskIdIsNull(String id, String projectId);
    List<Task> findByProjectIdAndParentTaskIdIsNull(String projectId);
    Task findByIdAndProjectId(String id, String projectId);
    List<Task> findByIdIn(List<String> taskIds);
}
