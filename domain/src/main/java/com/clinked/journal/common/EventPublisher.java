package com.clinked.journal.common;

import com.clinked.journal.common.model.Event;

public interface EventPublisher<T extends Event> {

    void publish(T event) throws Exception;
}
