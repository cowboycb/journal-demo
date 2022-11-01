package com.clinked.journal.article.quartz;

import com.clinked.journal.create.model.ArticleCreatedEvent;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuartzScheduleWithRetryImpl implements QuartzScheduleWithRetry {
    private final SchedulerFactoryBean factory;

    @Override
    public void startScheduler(ArticleCreatedEvent event, JobDetail job, Trigger trigger) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        if (scheduler.checkExists(job.getKey())) {
            log.warn("Job exists and it was failed before, creating new job for article {}", event.getId());
            scheduler.deleteJob(job.getKey());
        }

        scheduler.scheduleJob(job, trigger);

        log.debug("Starting Scheduler job for article {}, for date {}", event.getId(), event.getPublishDate());
        scheduler.start();
    }
}
