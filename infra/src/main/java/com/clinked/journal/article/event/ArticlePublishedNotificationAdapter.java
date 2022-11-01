package com.clinked.journal.article.event;

import com.clinked.journal.article.event.publish.RetryPublish;
import com.clinked.journal.publish.model.ArticlePublishedEvent;
import com.clinked.journal.publish.port.ArticlePublishedNotificationPort;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticlePublishedNotificationAdapter implements ArticlePublishedNotificationPort {

    private final RetryPublish retryPublish;

    @Override
    public void publish(ArticlePublishedEvent event) {
        log.info("Event id {}", event.getPublishDate().toLocalDate());
        retryPublish.publishArticle(event);
    }

}
