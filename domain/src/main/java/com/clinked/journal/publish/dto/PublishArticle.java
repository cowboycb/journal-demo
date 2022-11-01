package com.clinked.journal.publish.dto;

import com.clinked.journal.common.model.UseCase;

import lombok.Data;

@Data
public class PublishArticle implements UseCase {
    private String articleId;
}
