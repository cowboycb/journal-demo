package com.clinked.journal.publish.port;

import com.clinked.journal.publish.model.ArticlePublishedEvent;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;

public class ArticlePublishedNotificationFakeAdapter
    implements ArticlePublishedNotificationPort {
    public static final LocalDateTime FAIL_PUBLISHNOTIFY_DATE = LocalDateTime.MAX;
    private ArticlePublishedEvent event;

    @Override
    public void publish(ArticlePublishedEvent event) throws Exception {
        this.event = event;
        if (FAIL_PUBLISHNOTIFY_DATE.equals(event.getPublishDate())) {
            throw new Exception();
        }
    }

    public void assertEventIdIsNotNull() {
        Assertions.assertNotNull(event.getId());
    }
}
