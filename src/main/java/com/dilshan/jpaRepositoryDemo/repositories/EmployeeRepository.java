package com.dilshan.jpaRepositoryDemo.repositories;

import com.dilshan.jpaRepositoryDemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e where e.email = ?1")
    Optional<Employee> findEmployeeByEmployeeEmail(String email);

    @Query(
            value = "SELECT * FROM employee WHERE first_name = :firstName AND age >= :age",
            nativeQuery = true)
    List<Employee> selectEmployeeWhereFistNameAndAgeGreaterOrEqual(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.id = ?1")
    int deleteEmployeeById(Long id);
}
