package com.clinked.journal.publish.port;

import com.clinked.journal.common.EventPublisher;
import com.clinked.journal.publish.model.ArticlePublishedEvent;

public interface ArticlePublishedNotificationPort extends EventPublisher<ArticlePublishedEvent> {
}
