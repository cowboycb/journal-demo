package com.clinked.journal.list;

import com.clinked.journal.common.DomainComponent;
import com.clinked.journal.common.UseCaseHandler;
import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.common.validator.Validator;
import com.clinked.journal.list.dto.PageOf;
import com.clinked.journal.list.usecase.ListArticle;

import java.util.List;

import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class ArticleListUseCaseHandler implements UseCaseHandler<PageOf<List<Article>>, ListArticle> {

    private final ArticlePort articlePort;
    private final Validator<ListArticle> articleListValidator;

    @Override
    public PageOf<List<Article>> handle(ListArticle listArticleRequest) {
        articleListValidator.validate(listArticleRequest);
        return articlePort.findAll(listArticleRequest);
    }
}
