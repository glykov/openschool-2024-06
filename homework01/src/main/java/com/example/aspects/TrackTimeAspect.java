package com.example.aspects;

import com.example.services.TrackingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Saves results of intercepted method execution time measurement.
 * Measurement logic is run synchronously.
 * Intercepted method should be annotated with @TrackTime.
 */
@Aspect
@Component
public class TrackTimeAspect {
    private static final Logger logger = LoggerFactory.getLogger(TrackTimeAspect.class);

    private final TrackingService service;

    public TrackTimeAspect(TrackingService service) {
        this.service = service;
    }

    @Around("@annotation(com.example.annotations.TrackTime)")
    public void track(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toLongString();

        long start = System.currentTimeMillis();
        joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;

        logger.info("Execution of method {} took {}", methodName, elapsed);
        service.saveAsync(methodName, elapsed);
    }
}
