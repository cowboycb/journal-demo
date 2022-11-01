package com.clinked.journal.report.rest;

import com.clinked.journal.report.dto.StatisticResponse;
import com.clinked.journal.report.service.ReportService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticResponse>> statistics(
        @RequestParam(name = "endDate", required = false) Optional<String> paramEndDate){

        LocalDate endDate = getDateParam(paramEndDate);

        List<StatisticResponse> statistics = reportService.querySevenDays(endDate);
        return ResponseEntity.ok(statistics);
    }

    private LocalDate getDateParam(Optional<String> dateParam) {
        return dateParam
            .map(s -> LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .orElse(LocalDate.now());
    }
}
