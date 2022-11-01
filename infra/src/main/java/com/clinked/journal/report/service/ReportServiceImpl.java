package com.clinked.journal.report.service;

import com.clinked.journal.report.dto.StatisticResponse;
import com.clinked.journal.report.jpa.StatisticRepository;
import com.clinked.journal.report.jpa.entity.StatisticEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final StatisticRepository statisticRepository;

    @Override
    public List<StatisticResponse> querySevenDays(LocalDate endDate) {
        LocalDate startDate = endDate.minusDays(6);
        List<StatisticEntity> statistics =
            statisticRepository.findAllByPublishDateBetween(startDate, endDate);
        return statistics.stream()
            .map(st -> new StatisticResponse(st.getPublishDate(), st.getCount()))
            .collect(Collectors.toList());
    }
}
