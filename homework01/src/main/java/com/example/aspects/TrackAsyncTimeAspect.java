package com.example.aspects;

import com.example.services.TrackingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Saves results of intercepted method execution time measurement.
 * Measurement logic is run asynchronously.
 * Intercepted method should be annotated with @TrackAsyncTime.
 */
@Aspect
@Component
public class TrackAsyncTimeAspect {
    @Autowired
    private TrackingService trackingService;

    private static final Logger logger = LoggerFactory.getLogger(TrackAsyncTimeAspect.class);

    @Around("@annotation(com.example.annotations.TrackAsyncTime)")
    public Object track(ProceedingJoinPoint joinPoint) {
        return CompletableFuture.runAsync(() -> {
            String methodName = joinPoint.getSignature().toLongString();
            try {
                long start = System.currentTimeMillis();
                joinPoint.proceed();
                long elapsed = System.currentTimeMillis() - start;
                logger.info("Execution of method {} took {}", methodName, elapsed);
                trackingService.saveAsync(methodName, elapsed);
            } catch (Throwable t) {
                logger.error("Exception while tracking {} -- {}", methodName, t.getMessage());
            }
        });
    }
}
