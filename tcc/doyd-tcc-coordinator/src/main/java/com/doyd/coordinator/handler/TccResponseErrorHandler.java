package com.doyd.coordinator.handler;

import com.doyd.dps.context.ServletContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * @author Zhao Junjian
 */
@Slf4j
public class TccResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // always ingore exception
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.info("request id '{}' error response '{}'", ServletContextHolder.fetchRequestId(), response);
    }
}
