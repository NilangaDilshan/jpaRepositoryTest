package com.dilshan.jpaRepositoryDemo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Entity(name = "EmployeeIdCard")
@Table(
        name = "employee_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "employee_id_card_number_unique",
                        columnNames = "card_number"
                )
        }
)
public class EmployeeIdCard {

    @Id
    @SequenceGenerator(
            name = "employee_id_card_sequence",
            sequenceName = "employee_id_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_id_card_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    @Getter
    private Long id;
    @Getter
    @Column(
            name = "card_number",
            nullable = false,
            length = 15
    )
    private String cardNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "employee_id_fk"
            )
    )
    private Employee employee;

    public EmployeeIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public EmployeeIdCard(String cardNumber, Employee employee) {
        this.cardNumber = cardNumber;
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "EmployeeIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
