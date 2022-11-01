package com.clinked.journal.article.event.publish;

import com.clinked.journal.publish.model.ArticlePublishedEvent;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface RetryPublish {
    @Retryable(value = Throwable.class, maxAttemptsExpression = "${journal.retry.maxAttempts}",
        backoff = @Backoff(delayExpression = "${journal.retry.maxDelay}"))
    void publishArticle(ArticlePublishedEvent event);

    @Recover
    void recover(Exception e, ArticlePublishedEvent event);

}
