package com.example.model.inner;

import com.example.annotations.TrackAsyncTime;
import com.example.annotations.TrackTime;
import com.example.model.MethodsToTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AnotherTestClass {
    private final Logger logger = LoggerFactory.getLogger(MethodsToTest.class);

    @TrackAsyncTime
    public void testMethod() {
//        logger.info("Starting Test Method");
        try {
            TimeUnit.MILLISECONDS.sleep(350);
        } catch (InterruptedException e) {}
//        logger.info("Finishing Test Method");
    }

    @TrackTime
    public void anotherMethod() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {}
    }
}
