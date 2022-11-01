package com.clinked.journal.common.article.port;

import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.create.usecase.CreateArticle;
import com.clinked.journal.list.dto.PageOf;
import com.clinked.journal.list.usecase.ListArticle;

import java.util.List;

public interface ArticlePort {
    Article create(CreateArticle createArticleRequest);

    PageOf<List<Article>> findAll(ListArticle listArticleRequest);

    boolean publish(Article article);

    Article findById(String articleId);
}
