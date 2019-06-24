package com.doyd.eventdriven.strategy;

import com.doyd.eventdriven.entity.EventPublisher;
import com.doyd.eventdriven.entity.enums.EventStatusEnum;
import com.doyd.eventdriven.persistence.EventPublisherMapper;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * @author Zhao Junjian
 */
public enum RepublishPendingEventStrategy implements BatchFetchEventStrategy {
    SINGLETON;

    @Override
    public Set<EventPublisher> execute(EventPublisherMapper mapper) {
        // 取出3秒前已经发送过至队列但是没有收到ack请求的消息，并进行重试
        return mapper.selectLimitedEntityByEventStatusBeforeTheSpecifiedUpdateTime(EventStatusEnum.PENDING, 300, OffsetDateTime.now().minusSeconds(3));
    }
}
