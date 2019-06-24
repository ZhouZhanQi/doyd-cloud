package com.doyd.eventdriven.handler;


import com.doyd.db.handler.GenericTypeHandler;
import com.doyd.eventdriven.entity.enums.EventStatusEnum;

/**
 * @author Zhao Junjian
 */
public class EventStatusTypeHandler extends GenericTypeHandler<EventStatusEnum> {
    @Override
    public int getEnumIntegerValue(EventStatusEnum parameter) {
        return parameter.getStatus();
    }
}
