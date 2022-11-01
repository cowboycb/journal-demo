package com.clinked.journal.create.model;

import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.model.Event;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleCreatedEvent implements Event {
    private String id;
    private LocalDateTime publishDate;

    public static ArticleCreatedEvent from(Article article) {
        return ArticleCreatedEvent.builder()
            .id(article.getId())
            .publishDate(article.getPublishDate())
            .build();
    }
}
