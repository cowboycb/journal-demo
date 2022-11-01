package com.clinked.journal.common.article.port;

import com.clinked.journal.common.article.model.Article;
import com.clinked.journal.create.port.ArticleCreatedNotificationFakeAdapter;
import com.clinked.journal.create.usecase.CreateArticle;
import com.clinked.journal.list.dto.PageOf;
import com.clinked.journal.list.usecase.ListArticle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticleFakeAdapter implements ArticlePort {

    public static final String FAIL_CREATENOTIFY_TITLE = "FailTitle";
    public static final String FAIL_PUBLISH_ID = "publishFailId";
    public static final String FAIL_PUBLISHNOTIFY_ID = "publishFailNotifyId";

    List<Article> articles = new ArrayList<>();

    @Override
    public Article create(CreateArticle createArticleRequest) {
        Article article = new Article();
        article.setAuthor(createArticleRequest.getAuthor());
        article.setTitle(createArticleRequest.getTitle());
        article.setContent(createArticleRequest.getContent());
        article.setPublishDate(createArticleRequest.getPublishDate());
        article.setId(UUID.randomUUID().toString());
        if (FAIL_CREATENOTIFY_TITLE.equals(createArticleRequest.getTitle())) {
            article.setId(ArticleCreatedNotificationFakeAdapter.FAIL_EVENT_ARTICLE_ID);
        }
        articles.add(article);
        return article;
    }

    @Override
    public PageOf<List<Article>> findAll(ListArticle listArticleRequest) {
        PageOf<List<Article>> pageOf = new PageOf<>();
        pageOf.setPage(listArticleRequest.getPage());
        List<Article> sublist = getSublist(listArticleRequest);
        pageOf.setSize(sublist.size());
        pageOf.setData(sublist);
        pageOf.setTotal(articles.size());
        return pageOf;
    }

    private List<Article> getSublist(ListArticle listArticleRequest) {
        int fromIndex = (listArticleRequest.getPage() - 1) * listArticleRequest.getSize();
        int lastIndex = fromIndex + listArticleRequest.getSize();
        int toIndex = Math.min(lastIndex, (articles.size() - 1));
        return articles.subList(fromIndex == 0 ? 0 : (fromIndex - 1), toIndex);
    }

    @Override
    public boolean publish(Article article) {
        article.setPublished(false);
        if (!FAIL_PUBLISH_ID.equals(article.getId())) {
            article.setPublished(true);
        }
        return article.isPublished();
    }

    @Override
    public Article findById(String articleId) {
        Article article = new Article();
        article.setPublishDate(LocalDateTime.now().plusHours(2));
        article.setId(articleId);
        if (FAIL_PUBLISHNOTIFY_ID.equals(articleId)) {
            article.setPublishDate(LocalDateTime.MAX);
        }
        return article;
    }
}