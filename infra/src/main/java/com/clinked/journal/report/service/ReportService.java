package com.clinked.journal.report.service;

import com.clinked.journal.report.dto.StatisticResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<StatisticResponse> querySevenDays(LocalDate startDate);
}
