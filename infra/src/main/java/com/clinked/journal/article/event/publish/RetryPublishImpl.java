package com.clinked.journal.article.event.publish;

import com.clinked.journal.publish.model.ArticlePublishedEvent;
import com.clinked.journal.report.jpa.StatisticRepository;
import com.clinked.journal.report.jpa.entity.StatisticEntity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryPublishImpl implements RetryPublish {

    private final StatisticRepository statisticRepository;

    @Override
    public synchronized void publishArticle(ArticlePublishedEvent event) {
        StatisticEntity statisticEntity = statisticRepository.findByPublishDate(event.getPublishDate().toLocalDate())
            .orElse(new StatisticEntity());
        statisticEntity.setCount(statisticEntity.getCount() + 1);
        statisticEntity.setPublishDate(event.getPublishDate().toLocalDate());
        statisticRepository.saveAndFlush(statisticEntity);
    }

    @Override
    public void recover(Exception e, ArticlePublishedEvent event) {
        log.error("in Recover Exception {}, Event date {}", e.getMessage(), event.getPublishDate().toLocalDate());
    }
}
