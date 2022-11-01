package com.clinked.journal.create.port;

import com.clinked.journal.common.EventPublisher;
import com.clinked.journal.create.model.ArticleCreatedEvent;

public interface ArticleCreatedNotificationPort extends EventPublisher<ArticleCreatedEvent> {
}
