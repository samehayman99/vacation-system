package com.project.vacation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_vacation_balance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employee", "vacationType"})
@EqualsAndHashCode(exclude = {"employee", "vacationType"})
public class EmployeeVacationBalance {

    @EmbeddedId
    private EmployeeVacationBalanceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empId")
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vacTypeId")
    @JoinColumn(name = "vac_type_id")
    private VacationType vacationType;

    @Column(name = "days_remaining")
    private Integer daysRemaining;
}

