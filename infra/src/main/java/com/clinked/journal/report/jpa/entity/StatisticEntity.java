package com.clinked.journal.report.jpa.entity;

import com.clinked.journal.common.jpa.BaseEntity;

import java.time.LocalDate;

import org.springframework.data.annotation.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
public class StatisticEntity extends BaseEntity {
    @Column(name = "publish_date", nullable = false, unique = true)
    private LocalDate publishDate;

    @Column(name = "count")
    private Long count = 0L;

    @Version
    private Integer version;
}
