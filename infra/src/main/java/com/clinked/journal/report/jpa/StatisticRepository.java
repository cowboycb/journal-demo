package com.clinked.journal.report.jpa;

import com.clinked.journal.report.jpa.entity.StatisticEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, UUID> {
    Optional<StatisticEntity> findByPublishDate(LocalDate date);

    @Query("select s from StatisticEntity  s where s.publishDate >= :startDate and s.publishDate <= :endDate")
    List<StatisticEntity> findAllByPublishDateBetween(LocalDate startDate, LocalDate endDate);
}
