package com.doyd.eventdriven.strategy;

import com.doyd.eventdriven.entity.EventPublisher;
import com.doyd.eventdriven.persistence.EventPublisherMapper;

import java.util.Set;

/**
 * @author Zhao Junjian
 */
public interface BatchFetchEventStrategy {
    Set<EventPublisher> execute(EventPublisherMapper mapper);
}
