package com.clinked.journal.article.quartz;

import com.clinked.journal.create.model.ArticleCreatedEvent;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface QuartzScheduleWithRetry {

    @Retryable(value = SchedulerException.class, maxAttemptsExpression = "${journal.retry.maxAttempts}",
        backoff = @Backoff(delayExpression = "${journal.retry.maxDelay}"))
    void startScheduler(ArticleCreatedEvent event, JobDetail job, Trigger trigger) throws SchedulerException;
}
