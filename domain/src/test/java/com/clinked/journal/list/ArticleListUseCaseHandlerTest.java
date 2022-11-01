package com.clinked.journal.list;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.article.port.ArticleFakeAdapter;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.common.validator.ValidationException;
import com.clinked.journal.common.validator.Validator;
import com.clinked.journal.create.usecase.CreateArticle;
import com.clinked.journal.list.dto.PageOf;
import com.clinked.journal.list.usecase.ListArticle;
import com.clinked.journal.list.validator.ArticleListValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArticleListUseCaseHandlerTest {
    private ArticleListUseCaseHandler articleListUseCaseHandler;
    private ArticlePort articleAdapter;

    @BeforeEach
    void setUp() {
        articleAdapter = new ArticleFakeAdapter();
        Validator<ListArticle> articleListValidator = new ArticleListValidator();
        articleListUseCaseHandler = new ArticleListUseCaseHandler(articleAdapter, articleListValidator);
    }

    private ListArticle createListArticle(Integer page, Integer size) {
        ListArticle la = new ListArticle();
        la.setPage(page);
        la.setSize(size);
        return la;
    }

    @Test
    void when_RequestIsNull_throwsValidationException() {
        assetThrowsValidation(null, "parameters");
    }

    @Test
    void when_PageParameterIsNull_throwsValidationException() {
        ListArticle listArticle = createListArticle(null, 10);
        assetThrowsValidation(listArticle, "page");
    }

    @Test
    void when_PageParameterIsInvalid_throwsValidationException() {
        ListArticle listArticle = createListArticle(-1, 10);
        assetThrowsValidation(listArticle, "page");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSizeParams")
    void when_SizeParameterIsInvalid_throwsValidationException(Integer size) {
        ListArticle listArticle = createListArticle(1, size);
        assetThrowsValidation(listArticle, "size");
    }

    @Test
    void when_RequestIsValid_returnFirstPageSuccess() {
        for (int i = 0; i <12; i++) {
            articleAdapter.create(createValidArticleObject());
        }
        ListArticle listArticle = createListArticle(1, 10);
        PageOf<List<Article>> actual = articleListUseCaseHandler.handle(listArticle);
        assertEquals(1, actual.getPage());
        assertEquals(10, actual.getSize());
        assertEquals(12, actual.getTotal());
        assertEquals(10, actual.getData().size());
    }

    @Test
    void when_RequestIsValid_returnSecondPageValid() {
        for (int i = 0; i <12; i++) {
            articleAdapter.create(createValidArticleObject());
        }
        ListArticle listArticle = createListArticle(2, 10);
        PageOf<List<Article>> actual = articleListUseCaseHandler.handle(listArticle);
        assertEquals(2, actual.getPage());
        assertEquals(2, actual.getSize());
        assertEquals(12, actual.getTotal());
        assertEquals(2, actual.getData().size());
    }

    private CreateArticle createValidArticleObject() {
        CreateArticle createArticle = new CreateArticle();
        createArticle.setTitle("title");
        createArticle.setContent("content");
        createArticle.setAuthor("author");
        createArticle.setPublishDate(LocalDateTime.now().plusHours(1));
        return createArticle;
    }

    private static Stream<Arguments> provideInvalidSizeParams() {
        return Stream.of(
            Arguments.of(0),
            Arguments.of(-1)
        );
    }

    private void assetThrowsValidation(ListArticle listArticle, String content) {
        ValidationException actualException =
            Assertions.assertThrows(ValidationException.class, () -> articleListUseCaseHandler.handle(listArticle));
        Assertions.assertTrue(actualException.isNotEmpty());
        Assertions.assertTrue(StringUtils.containsIgnoreCase(actualException.getMessage(), content));
    }
}