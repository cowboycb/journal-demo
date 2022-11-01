package com.clinked.journal.article.quartz;

import com.clinked.journal.common.UseCaseHandler;
import com.clinked.journal.publish.dto.PublishArticle;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublishArticleJob implements Job {

    public static final String KEY_ARTICLE_ID = "articleId";

    private final UseCaseHandler<Boolean, PublishArticle> articlePublishUseCaseHandler;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String articleId = jobDataMap.get(KEY_ARTICLE_ID).toString();
        PublishArticle publishArticle = new PublishArticle();
        publishArticle.setArticleId(articleId);
        articlePublishUseCaseHandler.handle(publishArticle);
    }
}
