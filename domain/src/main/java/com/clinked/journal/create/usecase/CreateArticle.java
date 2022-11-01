package com.clinked.journal.create.usecase;

import com.clinked.journal.common.model.UseCase;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateArticle implements UseCase {
    private String title;
    private String author;
    private String content;
    private LocalDateTime publishDate;
}
