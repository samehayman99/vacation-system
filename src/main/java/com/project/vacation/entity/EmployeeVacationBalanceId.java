package com.project.vacation.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class EmployeeVacationBalanceId {

    private Long empId;

    private Long vacTypeId;
}
