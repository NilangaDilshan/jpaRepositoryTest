package com.dilshan.jpaRepositoryDemo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity(name = "Employee")
@Table(name = "employee", uniqueConstraints = {
        @UniqueConstraint(name = "employee_email_unique", columnNames = "email")
})
public class Employee {

    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;

    @Getter
    @Setter
    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToOne(
            mappedBy = "employee",
            orphanRemoval = true
    )
    private EmployeeIdCard employeeIdCard;

    @OneToMany(
            mappedBy = "employee",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}/*,
            fetch = FetchType.LAZY*/
    )
    private List<Book> books = new ArrayList<>();

    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "employee")
    private List<Enrolment> enrolments = new ArrayList<>();

    @Getter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<>();

    public Employee(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public void addBook(Book book) {
        if (!this.books.contains(book)) {
            this.books.add(book);
            book.setEmployee(this);
        }
    }

    public void removeBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setEmployee(null);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addEnrolment(Enrolment enrolment) {
        if (!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        this.enrolments.remove(enrolment);
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public void removeProject(Project project) {
        this.projects.add(project);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
