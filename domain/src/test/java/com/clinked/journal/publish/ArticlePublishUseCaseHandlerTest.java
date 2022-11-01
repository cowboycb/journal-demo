package com.clinked.journal.publish;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.clinked.journal.common.article.port.ArticleFakeAdapter;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.common.validator.ValidationException;
import com.clinked.journal.publish.dto.PublishArticle;
import com.clinked.journal.publish.port.ArticlePublishedNotificationFakeAdapter;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ArticlePublishUseCaseHandlerTest {

    private ArticlePublishUseCaseHandler articlePublishUseCaseHandler;
    private ArticlePublishedNotificationFakeAdapter notificationAdapter;

    @BeforeEach
    void setUp() {
        notificationAdapter = new ArticlePublishedNotificationFakeAdapter();
        ArticlePort articleAdapter = new ArticleFakeAdapter();
        articlePublishUseCaseHandler = new ArticlePublishUseCaseHandler(articleAdapter,
            notificationAdapter);
    }

    @Test
    void when_publishRequestIsNull_thenThrowException() {
        ValidationException actualException =
            Assertions.assertThrows(ValidationException.class, () -> articlePublishUseCaseHandler.handle(null));
        assertNotNull(actualException.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void when_publishArticleIdIsNull_thenThrowException(String articleId) {
        PublishArticle publishArticle = new PublishArticle();
        publishArticle.setArticleId(articleId);
        ValidationException actualException =
            Assertions.assertThrows(ValidationException.class, () -> articlePublishUseCaseHandler.handle(publishArticle));
        assertNotNull(actualException.getMessage());
    }

    @Test
    void when_publishArticleWithValidId_returnSuccess() {
        PublishArticle publishArticle = new PublishArticle();
        publishArticle.setArticleId(UUID.randomUUID().toString());
        Boolean result = articlePublishUseCaseHandler.handle(publishArticle);
        assertTrue(result);
        notificationAdapter.assertEventIdIsNotNull();
    }

    @Test
    void when_publishArticleWithValidId_publishFail() {
        PublishArticle publishArticle = new PublishArticle();
        publishArticle.setArticleId(ArticleFakeAdapter.FAIL_PUBLISH_ID);
        Boolean result = articlePublishUseCaseHandler.handle(publishArticle);
        assertFalse(result);
    }

    @Test
    void when_publishArticleSuccessButNotifyFailed() {
        PublishArticle publishArticle = new PublishArticle();
        publishArticle.setArticleId(ArticleFakeAdapter.FAIL_PUBLISHNOTIFY_ID);
        Boolean result = articlePublishUseCaseHandler.handle(publishArticle);
        assertTrue(result);
    }
}