package com.dilshan.jpaRepositoryDemo;

import com.dilshan.jpaRepositoryDemo.entities.*;
import com.dilshan.jpaRepositoryDemo.repositories.EmployeeCardRepository;
import com.dilshan.jpaRepositoryDemo.repositories.EmployeeRepository;
import com.dilshan.jpaRepositoryDemo.repositories.ProjectRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@Slf4j
public class JpaRepositoryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaRepositoryDemoApplication.class, args);
    }

    /*@Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository) {
        return args -> {
            employeeRepository.save(new Employee("Slayer", "Araya", "slayer@gmail.com", 100));
            employeeRepository.save(new Employee("Dimmu", "Shagrath", "shagrath@gmail.com", 200));
            employeeRepository.save(new Employee("Dimmu", "Galder", "galder@gmail.com", 150));
            log.info("Employee By email shagrath@gmail.com : {}", employeeRepository.findEmployeeByEmployeeEmail("shagrath@gmail.com"));
            log.info("Employee By firstname: Dimmu and Age greater than 75: {}",
                    employeeRepository.selectEmployeeWhereFistNameAndAgeGreaterOrEqual("Dimmu", 75));
            log.info("Delete From Employee by id 1: {}", employeeRepository.deleteEmployeeById(1L));

        };
    }*/

    /*@Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository, EmployeeCardRepository employeeCardRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Employee employee = new Employee(firstName, lastName, email, faker.number().numberBetween(50, 100));

            EmployeeIdCard employeeIdCard = new EmployeeIdCard("12345", employee);

            employeeCardRepository.save(employeeIdCard);

            log.info("Employee Id Card by id 1: {}", employeeCardRepository.findAll().toString());
            log.info("Employee by id:1 {}", employeeRepository.findById(1L).get());
        };
    }*/
    @Bean
    @Transactional(readOnly = true)
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository, EmployeeCardRepository employeeCardRepository,
                                        ProjectRepository projectRepository) {
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Employee employee = new Employee(firstName, lastName, email, faker.number().numberBetween(50, 100));

            EmployeeIdCard employeeIdCard = new EmployeeIdCard("12345", employee);

            employee.addBook(new Book("Java Dummy", LocalDateTime.now()));
            employee.addBook(new Book("Python for Dummy", LocalDateTime.now()));

            employee.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    employee,
                    new Course("Kubernetes", "DevOps"),
                    LocalDateTime.now()
            ));
            /*Project project = projectRepository.save(new Project("Java Devops Project", LocalDateTime.now()));
            employee.addProject(project);*/

            employee.addProject(new Project("Java Devops Project", LocalDateTime.now()));

            employeeCardRepository.save(employeeIdCard);
            employeeRepository.findById(1L).ifPresent(employee1 -> {
                List<Book> books = employee.getBooks();
                books.forEach(book -> log.info("Books Burrowed By {}: {}", book.getEmployee().getFirstName(), book.toString()));
            });
            //employeeRepository.deleteEmployeeById(1L);
            employeeRepository.deleteById(1L);
        };
    }
}
