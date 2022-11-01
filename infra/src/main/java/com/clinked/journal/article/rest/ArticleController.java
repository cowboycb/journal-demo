package com.clinked.journal.article.rest;

import com.clinked.journal.article.dto.ArticleResponse;
import com.clinked.journal.article.dto.CreateArticleRequest;
import com.clinked.journal.common.UseCaseHandler;
import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.create.usecase.CreateArticle;
import com.clinked.journal.list.dto.PageOf;
import com.clinked.journal.list.usecase.ListArticle;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class ArticleController {

    private final UseCaseHandler<Article, CreateArticle> articleCreateUseCaseHandler;
    private final UseCaseHandler<PageOf<List<Article>>, ListArticle> articleListUseCaseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArticleResponse> create(@Valid @RequestBody CreateArticleRequest request){
        Article article = articleCreateUseCaseHandler.handle(request.toModel());
        return ResponseEntity.ok(ArticleResponse.fromModel(article));
    }

    @GetMapping
    public ResponseEntity<PageOf<List<ArticleResponse>>> list(@Valid ListArticle request){
        PageOf<List<Article>> articles = articleListUseCaseHandler.handle(request);
        PageOf<List<ArticleResponse>> articlesPage = articles.clone(ArticleResponse.fromModels(articles.getData()));
        return ResponseEntity.ok(articlesPage);
    }
}
