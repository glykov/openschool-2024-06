package com.example;

import com.example.model.MethodsToTest;
import com.example.model.inner.AnotherTestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {
    @Autowired
    private MethodsToTest methods;
    @Autowired
    private AnotherTestClass atc;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        methods.testMethod();
        methods.anotherMethod();
        atc.testMethod();
        atc.anotherMethod();
    }
}
