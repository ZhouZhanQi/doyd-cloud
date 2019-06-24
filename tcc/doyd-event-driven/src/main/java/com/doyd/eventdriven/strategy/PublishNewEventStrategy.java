package com.doyd.eventdriven.strategy;

import com.doyd.eventdriven.entity.EventPublisher;
import com.doyd.eventdriven.entity.enums.EventStatusEnum;
import com.doyd.eventdriven.persistence.EventPublisherMapper;

import java.util.Set;

/**
 * @author Zhao Junjian
 */
public enum PublishNewEventStrategy implements BatchFetchEventStrategy {
    SINGLETON;

    @Override
    public Set<EventPublisher> execute(EventPublisherMapper mapper) {
        return mapper.selectLimitedEntityByEventStatus(EventStatusEnum.NEW, 300);
    }
}
