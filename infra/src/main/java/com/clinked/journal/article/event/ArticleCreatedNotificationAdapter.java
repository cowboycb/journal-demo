package com.clinked.journal.article.event;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.clinked.journal.article.quartz.PublishArticleJob;
import com.clinked.journal.article.quartz.QuartzScheduleWithRetry;
import com.clinked.journal.create.model.ArticleCreatedEvent;
import com.clinked.journal.create.port.ArticleCreatedNotificationPort;

import java.time.ZoneId;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleCreatedNotificationAdapter implements ArticleCreatedNotificationPort {

    public static final String QUARTZ_JOB_NAME = "PublishArticleJob-";
    public static final String QUARTZ_TRIGGER_NAME = "PublishArticleTrigger-";
    public static final String QUARTZ_GROUP = "articleGroup";

    private final QuartzScheduleWithRetry quartzScheduleWithRetry;

    @Override
    @Async
    public void publish(ArticleCreatedEvent event) {
        JobDetail job = newJob(PublishArticleJob.class)
            .withIdentity(QUARTZ_JOB_NAME + event.getId(), QUARTZ_GROUP)
            .build();

        job.getJobDataMap().put(PublishArticleJob.KEY_ARTICLE_ID, event.getId());

        Trigger trigger = newTrigger()
            .forJob(job)
            .withIdentity(QUARTZ_TRIGGER_NAME + event.getId(), QUARTZ_GROUP)
            .startAt(Date.from(event.getPublishDate().atZone(ZoneId.systemDefault()).toInstant()))
            .build();

        try {
            quartzScheduleWithRetry.startScheduler(event, job, trigger);
        } catch (SchedulerException e) {
            log.error("Scheduler couldn't be started ", e);
        }
    }
}
