package com.example.controllers;

import com.example.model.TrackingData;
import com.example.services.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/executionTime")
public class TrackingController {
    private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    /**
     * Produces JSON data describing all invocations of all tracked methods
     * @return JSON data describing all invocations of all tracked methods
     */
    @Operation(summary = "Get all benchmarks")
    @GetMapping
    public ResponseEntity<List<TrackingData>> getAll() {
        var result = trackingService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * Produces JSON data describing average execution time of specified method
     * @param methodName - String containing method name passed as path variable
     * @return long value for average execution time in milliseconds
     */
    @Operation(summary = "Get average execution time for selected method")
    @GetMapping("/avg/{methodName}")
    public ResponseEntity<Long> getAverageTimeForMethod(@PathVariable("methodName") String methodName) {
        Long result = trackingService.findMethodAverage(methodName);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * Produces JSON data describing total execution time of all calls to specified method
     * @param methodName - String containing method name passed as path variable
     * @return long value for total execution time in milliseconds
     */
    @Operation(summary = "Get total execution time for all method calls")
    @GetMapping("/total/{methodName}")
    public ResponseEntity<Long> getTotalTimeForMethod(@PathVariable("methodName") String methodName) {
        Long result = trackingService.findMethodTotal(methodName);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * Produces JSON data describing average execution time for methods of specified group (package or class)
     * @param packageName - String containing package name passed as path variable
     * @return long value for average execution time in milliseconds
     */
    @Operation(summary = "Get average execution time for for methods in specified group (package)")
    @GetMapping("/grp/avg/{packageName}")
    public ResponseEntity<Long> getAverageTimeForGroup(@PathVariable("packageName") String packageName) {
        Long result = trackingService.findGroupAverage(packageName);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * Produces JSON data describing total execution time of all calls to methods of specified group (package or class)
     * @param packageName - String containing package name passed as path variable
     * @return long value for total execution time in milliseconds
     */
    @Operation(summary = "Get total execution time for all method calls")
    @GetMapping("/grp/total/{packageName}")
    public ResponseEntity<Long> getTotalTimeForGroup(@PathVariable("packageName") String packageName) {
        Long result = trackingService.findGroupTotal(packageName);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
