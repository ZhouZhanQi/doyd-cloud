package com.doyd.eventdriven.config;

import lombok.Value;

/**
 * @author Zhao Junjian
 */
@Value
public class MessageRoute {
    private String exchange;

    private String routeKey;
}
