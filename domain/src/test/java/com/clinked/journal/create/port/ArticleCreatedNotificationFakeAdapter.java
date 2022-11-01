package com.clinked.journal.create.port;

import com.clinked.journal.create.model.ArticleCreatedEvent;

import org.junit.jupiter.api.Assertions;

public class ArticleCreatedNotificationFakeAdapter implements ArticleCreatedNotificationPort {

    public static final String FAIL_EVENT_ARTICLE_ID = "111";
    private ArticleCreatedEvent event;

    @Override
    public void publish(ArticleCreatedEvent event) throws Exception {
        this.event = event;
        if (FAIL_EVENT_ARTICLE_ID.equals(event.getId())) {
            throw new Exception();
        }
    }

    public void assertEventHasValidDate() {
        Assertions.assertNotNull(event.getPublishDate());
    }
}
