package com.pcms.dataaccess.repository;

import com.pcms.model.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository
        extends JpaRepository<Period, Byte> { }
