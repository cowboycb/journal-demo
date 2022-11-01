package com.clinked.journal.report.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticResponse {
    private LocalDate date;
    private Long count;
}
