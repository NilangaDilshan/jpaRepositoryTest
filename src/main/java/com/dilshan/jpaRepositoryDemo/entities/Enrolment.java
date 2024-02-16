package com.dilshan.jpaRepositoryDemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(
            name = "employee_id",
            foreignKey = @ForeignKey(
                    name = "enrolment_employee_id_fk"
            )
    )
    private Employee employee;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(
                    name = "enrolment_course_id_fk"
            )
    )
    private Course course;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    public Enrolment(EnrolmentId id,
                     Employee employee,
                     Course course,
                     LocalDateTime createdAt) {
        this.id = id;
        this.employee = employee;
        this.course = course;
        this.createdAt = createdAt;
    }

    public Enrolment(Employee employee,
                     Course course,
                     LocalDateTime createdAt) {
        this.employee = employee;
        this.course = course;
        this.createdAt = createdAt;
    }
}
