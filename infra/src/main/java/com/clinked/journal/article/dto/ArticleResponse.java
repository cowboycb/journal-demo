package com.clinked.journal.article.dto;

import com.clinked.journal.common.article.model.Article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleResponse {
    private String id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime publishDate;

    public static ArticleResponse fromModel(Article article) {
        return ArticleResponse.builder()
            .id(article.getId())
            .author(article.getAuthor())
            .title(article.getTitle())
            .content(article.getContent())
            .publishDate(article.getPublishDate())
            .build();
    }

    public static List<ArticleResponse> fromModels(List<Article> articles) {
        if (CollectionUtils.isEmpty(articles)) {
            return new ArrayList<>();
        }
        return articles.stream()
            .map(ArticleResponse::fromModel)
            .collect(Collectors.toList());
    }
}
