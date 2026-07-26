package com.project.vacation.repository;

import com.project.vacation.entity.VacationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationTypeRepository extends JpaRepository<VacationType, Long> {
}
