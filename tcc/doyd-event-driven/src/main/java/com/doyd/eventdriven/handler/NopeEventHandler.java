package com.doyd.eventdriven.handler;

import com.doyd.eventdriven.entity.EventSubscriber;
import com.doyd.eventdriven.entity.enums.EventStatusEnum;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zhao Junjian
 */
public class NopeEventHandler extends EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NopeEventHandler.class);

    @Override
    public void handle(EventSubscriber subscriber) {
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(subscriber.getId());
        if (getMapper().updateEventStatusByPrimaryKeyInCasMode(subscriber.getId(), EventStatusEnum.NEW, EventStatusEnum.NOT_FOUND) > 0) {
            LOGGER.error("event which id is {} has to change status from NEW to NOT_FOUND due to threr is not a match handler.", subscriber.getId());
        }
    }

}
