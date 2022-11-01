package com.clinked.journal.common.article.model;

import com.clinked.journal.create.usecase.CreateArticle;

import lombok.Data;

@Data
public class Article extends CreateArticle {
    private String id;
    private boolean isPublished;
}
