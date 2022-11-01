package com.clinked.journal.create;

import com.clinked.journal.common.DomainComponent;
import com.clinked.journal.common.UseCaseHandler;
import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.common.validator.Validator;
import com.clinked.journal.create.model.ArticleCreatedEvent;
import com.clinked.journal.create.port.ArticleCreatedNotificationPort;
import com.clinked.journal.create.usecase.CreateArticle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class ArticleCreateUseCaseHandler implements UseCaseHandler<Article, CreateArticle> {

    private final ArticlePort articlePort;
    private final Validator<CreateArticle> articleCreateValidator;
    private final ArticleCreatedNotificationPort articleCreatedNotificationPort;

    @Override
    public Article handle(CreateArticle createArticle) {
        articleCreateValidator.validate(createArticle);
        Article article = articlePort.create(createArticle);
        sendNotification(article);
        return article;
    }

    private void sendNotification(Article article) {
        try {
            articleCreatedNotificationPort.publish(ArticleCreatedEvent.from(article));
            log.debug("Notification is sent for created article {} ", article.getId());
        } catch (Exception e) {
            log.error("Article created notification couldn't be sent for article {}", article.getId(), e);
        }
    }
}
