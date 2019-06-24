package com.doyd.dps.aspect;

import com.doyd.core.exceptions.ManualException;
import com.doyd.dps.config.ManualExceptionProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author zhouzq
 */
@Aspect
public class ManualExceptionAspect implements Ordered {
    private final int order;
    private final ManualExceptionProperties properties;
    private static final Random RANDOM = new SecureRandom();

    public ManualExceptionAspect(int order, ManualExceptionProperties properties) {
        this.order = order;
        this.properties = properties;
    }

    @Around("@annotation(com.doyd.dps.annotations.RandomlyThrowsException)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (properties.isEnabled()) {
            if (RANDOM.nextInt(100) % properties.getFactor() == 0) {
                throw new ManualException("manual exception");
            }
        }
        return joinPoint.proceed();
    }

    @Override
    public int getOrder() {
        return order;
    }
}
