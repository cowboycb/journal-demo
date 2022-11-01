package com.clinked.journal.create;

import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.article.port.ArticleFakeAdapter;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.common.validator.ValidationException;
import com.clinked.journal.common.validator.Validator;
import com.clinked.journal.create.port.ArticleCreatedNotificationFakeAdapter;
import com.clinked.journal.create.usecase.CreateArticle;
import com.clinked.journal.create.validator.ArticleCreateValidator;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleCreateUseCaseHandlerTest {

    private ArticleCreateUseCaseHandler articleCreateUseCaseHandler;
    private ArticleCreatedNotificationFakeAdapter createNotificationPort;

    @BeforeEach
    void setup() {
        ArticlePort articlePort = new ArticleFakeAdapter();
        Validator<CreateArticle> articleCreateValidator = new ArticleCreateValidator();
        createNotificationPort = new ArticleCreatedNotificationFakeAdapter();
        articleCreateUseCaseHandler = new ArticleCreateUseCaseHandler(articlePort,
            articleCreateValidator, createNotificationPort);
    }

    private CreateArticle createValidArticleObject() {
        CreateArticle createArticle = new CreateArticle();
        createArticle.setTitle("title");
        createArticle.setContent("content");
        createArticle.setAuthor("author");
        createArticle.setPublishDate(LocalDateTime.now().plusHours(1));
        return createArticle;
    }

    @Test
    void when_allInputsValid_returnSuccess() {
        CreateArticle createArticle = createValidArticleObject();

        Article actual = articleCreateUseCaseHandler.handle(createArticle);
        Assertions.assertNotNull(actual.getId());
        createNotificationPort.assertEventHasValidDate();
    }

    @Test
    void when_allInputsValid_returnSuccess_butNotificationFailed() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setTitle(ArticleFakeAdapter.FAIL_CREATENOTIFY_TITLE);

        Article actual = articleCreateUseCaseHandler.handle(createArticle);

        Assertions.assertNotNull(actual.getId());
    }

    @Test
    void when_RequestIsNull_throwValidationException() {
        CreateArticle createArticle = null;
        assetThrowsValidation(createArticle, "request");
    }

    @Test
    void when_titleIsNull_throwValidationException() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setTitle(null);
        assetThrowsValidation(createArticle, "title");
    }

    @Test
    void when_titleIsGreaterThanValidSize_throwValidationException() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setTitle(RandomStringUtils.randomAlphabetic(101));
        assetThrowsValidation(createArticle, "title");
    }

    @Test
    void when_contentIsNull_throwValidationException() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setContent(null);

        assetThrowsValidation(createArticle, "content");
    }

    @Test
    void when_authorIsNull_throwValidationException() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setAuthor(null);

        assetThrowsValidation(createArticle, "author");
    }

    @Test
    void when_publishDateIsNull_throwValidationException() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setPublishDate(null);

        assetThrowsValidation(createArticle, "publish");
    }

    @Test
    void when_publishDateIsLessThanNow_throwValidationException() {
        CreateArticle createArticle = createValidArticleObject();
        createArticle.setPublishDate(LocalDateTime.now().minusMinutes(1));

        assetThrowsValidation(createArticle, "publish");
    }

    private void assetThrowsValidation(CreateArticle createArticle, String content) {
        ValidationException actualException =
            Assertions.assertThrows(ValidationException.class, () -> articleCreateUseCaseHandler.handle(createArticle));
        Assertions.assertTrue(actualException.isNotEmpty());
        Assertions.assertTrue(StringUtils.containsIgnoreCase(actualException.getMessage(), content));
    }
}