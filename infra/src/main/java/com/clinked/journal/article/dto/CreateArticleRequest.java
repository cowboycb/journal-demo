package com.clinked.journal.article.dto;

import com.clinked.journal.create.usecase.CreateArticle;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateArticleRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String content;
    @NotNull
    @JsonProperty("publish_date")
    private LocalDateTime publishDate;

    public CreateArticle toModel() {
        CreateArticle ca = new CreateArticle();
        ca.setAuthor(this.author);
        ca.setTitle(this.title);
        ca.setContent(this.content);
        ca.setPublishDate(this.publishDate);
        return ca;
    }
}
