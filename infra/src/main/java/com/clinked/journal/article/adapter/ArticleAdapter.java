package com.clinked.journal.article.adapter;

import com.clinked.journal.article.jpa.ArticleRepository;
import com.clinked.journal.article.jpa.entity.ArticleEntity;
import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.common.article.port.ArticlePort;
import com.clinked.journal.create.usecase.CreateArticle;
import com.clinked.journal.list.dto.PageOf;
import com.clinked.journal.list.usecase.ListArticle;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleAdapter implements ArticlePort {

    private final ArticleRepository articleRepository;

    @Override
    public Article create(CreateArticle createArticleRequest) {
        ArticleEntity articleEntity = ArticleEntity.from(createArticleRequest);
        ArticleEntity article = articleRepository.save(articleEntity);
        return article.toModel();
    }

    @Override
    public PageOf<List<Article>> findAll(ListArticle listArticleRequest) {
        Pageable pageRequest = PageRequest.of(listArticleRequest.getPage() - 1, listArticleRequest.getSize());
        Page<ArticleEntity> articles = articleRepository.findAll(pageRequest);
        long total = articleRepository.count();
        List<Article> responseData = articles.getContent().stream()
            .map(ArticleEntity::toModel)
            .collect(Collectors.toList());
        PageOf<List<Article>> pageOf = new PageOf<>();
        pageOf.setPage(listArticleRequest.getPage());
        pageOf.setSize(responseData.size());
        pageOf.setTotal(total);
        pageOf.setData(responseData);
        return pageOf;
    }

    @Override
    public boolean publish(Article article) {
        article.setPublished(true);
        articleRepository.saveAndFlush(ArticleEntity.from(article));
        return true;
    }

    @Override
    public Article findById(String articleId) {
        return articleRepository.findById(UUID.fromString(articleId))
            .map(ArticleEntity::toModel)
            .orElseThrow();
    }
}
