/*
 * Copyright 2020.
 */

package com.exercise.scheduler.persistence.dao;

import com.exercise.scheduler.persistence.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by japili on 06/01/2020.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {


}
