package com.dilshan.jpaRepositoryDemo.repositories;

import com.dilshan.jpaRepositoryDemo.entities.EmployeeIdCard;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeCardRepository extends CrudRepository<EmployeeIdCard, Long> {
}
