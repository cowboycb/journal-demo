package com.clinked.journal.article.jpa.entity;

import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.jpa.BaseEntity;
import com.clinked.journal.create.usecase.CreateArticle;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class ArticleEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "is_published")
    private boolean isPublished;

    public static ArticleEntity from(CreateArticle createArticleRequest) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(createArticleRequest.getTitle());
        entity.setAuthor(createArticleRequest.getAuthor());
        entity.setContent(createArticleRequest.getContent());
        entity.setPublishDate(createArticleRequest.getPublishDate());
        return entity;
    }

    public static ArticleEntity from(Article article) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(UUID.fromString(article.getId()));
        entity.setTitle(article.getTitle());
        entity.setAuthor(article.getAuthor());
        entity.setContent(article.getContent());
        entity.setPublishDate(article.getPublishDate());
        entity.setPublished(article.isPublished());
        return entity;
    }

    public Article toModel() {
        Article article = new Article();
        article.setId(this.getId().toString());
        article.setTitle(this.getTitle());
        article.setAuthor(this.getAuthor());
        article.setContent(this.getContent());
        article.setPublishDate(this.getPublishDate());
        article.setPublished(this.isPublished());
        return article;
    }
}
