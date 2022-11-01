package com.clinked.journal.publish.model;

import com.clinked.journal.common.model.Event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ArticlePublishedEvent implements Event {
    private final LocalDateTime publishDate;

    public ArticlePublishedEvent(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }
}
