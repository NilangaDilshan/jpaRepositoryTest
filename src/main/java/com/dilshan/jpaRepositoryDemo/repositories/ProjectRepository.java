package com.dilshan.jpaRepositoryDemo.repositories;

import com.dilshan.jpaRepositoryDemo.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
