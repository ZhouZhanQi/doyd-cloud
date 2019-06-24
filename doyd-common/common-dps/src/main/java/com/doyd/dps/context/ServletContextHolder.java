package com.doyd.dps.context;

import com.doyd.dps.consts.RequestAttributeConst;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zhouzq
 * @date 2019/6/24
 * @desc
 */
@NoArgsConstructor
public class ServletContextHolder {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String fetchRequestId() {
        String requestId = (String) getRequest().getAttribute(RequestAttributeConst.REQUEST_ID);
        if (requestId == null) {
            requestId = Optional.ofNullable(getRequest().getHeader(RequestAttributeConst.REQUEST_ID)).orElse("x-" + UUID.randomUUID());
            getRequest().setAttribute(RequestAttributeConst.REQUEST_ID, requestId);
        }
        return requestId;
    }
}
