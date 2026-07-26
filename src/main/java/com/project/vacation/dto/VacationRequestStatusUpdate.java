package com.project.vacation.dto;
import com.project.vacation.entity.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequestStatusUpdate {
    @NotNull
    private RequestStatus status;
}
