package com.project.vacation.dto;
import com.project.vacation.entity.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequestResponse {
    private Long id;
    private Long empId;
    private String employeeName;
    private Long vacTypeId;
    private String vacationTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer daysRequested;
    private RequestStatus status;
    private LocalDateTime requestedAt;
}
