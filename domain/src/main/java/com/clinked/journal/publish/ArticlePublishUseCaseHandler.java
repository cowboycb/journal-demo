package com.clinked.journal.publish;

import com.clinked.journal.common.DomainComponent;
import com.clinked.journal.common.UseCaseHandler;
import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.common.validator.ValidationException;
import com.clinked.journal.publish.dto.PublishArticle;
import com.clinked.journal.publish.model.ArticlePublishedEvent;
import com.clinked.journal.publish.port.ArticlePublishedNotificationPort;

import org.apache.commons.lang3.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class ArticlePublishUseCaseHandler implements UseCaseHandler<Boolean, PublishArticle> {

    private final ArticlePort articlePort;
    private final ArticlePublishedNotificationPort articlePublishedNotificationPort;

    @Override
    public Boolean handle(PublishArticle publishArticle) {
        validate(publishArticle);
        Article article = articlePort.findById(publishArticle.getArticleId());
        boolean result = articlePort.publish(article);
        if (result) {
            sendNotification(article);
        } else {
            log.error("Article couldn't be published {}", publishArticle.getArticleId());
        }
        return result;
    }

    private void validate(PublishArticle publishArticle) {
        if (publishArticle == null || StringUtils.isBlank(publishArticle.getArticleId())) {
            throw new ValidationException("Article Id cannot be empty!");
        }
    }

    private void sendNotification(Article article) {
        try {
            articlePublishedNotificationPort.publish(new ArticlePublishedEvent(article.getPublishDate()));
            log.debug("Notification is sent for published article {} ", article.getId());
        } catch (Exception e) {
            log.error("Article published notification couldn't be sent for article {}", article.getId(), e);
        }
    }
}
