package com.example.services;

import com.example.model.TrackingData;
import com.example.repositories.TrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provides interaction between repository (dao object) and other parts of program
 */
@Service
public class TrackingService {
    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);

    private final TrackingRepository repository;

    public TrackingService(TrackingRepository repository) {
        this.repository = repository;
    }

    /**
     * Asynchronously passes the data of execution time measurement
     * to repository for storage
     * @param data - Object of TrackingData class
     * @return CompletableFuture object
     */
    public Object saveAsync(TrackingData data) {
        return CompletableFuture.runAsync(() -> {
            try {
                repository.save(data);
            } catch (Throwable t) {
                logger.error("Tracking service failed: {}", t.getMessage());
            }
        });
    }

    /**
     * Asynchronously passes the data of execution time measurement
     * to repository for storage
     * receives method name and execution time to create new object of TrackingData class
     * @param methodName - name of target method
     * @param executionTime - time of method execution in milliseconds
     * @return CompletableFuture object
     */
    public Object saveAsync(String methodName, long executionTime) {
        TrackingData data = new TrackingData();
        // dividing sting containing fully specified method name into method and package + class parts
        String pkgName = methodName.substring(0, methodName.lastIndexOf("."));
        methodName = methodName.substring(methodName.lastIndexOf(".") + 1);
        data.setMethodName(methodName);
        data.setPackageName(pkgName);
        data.setExecutedAt(LocalDateTime.now());
        data.setExecutionTime(executionTime);

        return CompletableFuture.runAsync(() -> {
            try {
                repository.save(data);
            } catch (Throwable t) {
                logger.error("Tracking service failed: {}", t.getMessage());
            }
        });
    }

    /**
     * At the moment just proxying call to repository method
     * @return
     */
    public List<TrackingData> findAll() {
        return repository.findAll();
    }

    /**
     * At the moment just proxying call to repository method
     * @return
     */
    public Long findMethodAverage(String methodName) {
        return repository.findMethodAverageTime(methodName);
    }

    /**
     * At the moment just proxying call to repository method
     * @return
     */
    public Long findMethodTotal(String methodName) {
        return repository.findMethodTotalTime(methodName);
    }

    /**
     * At the moment just proxying call to repository method
     * @return
     */
    public Long findGroupAverage(String packageName) {
        return repository.findGroupAverageTime(packageName);
    }

    /**
     * At the moment just proxying call to repository method
     * @return
     */
    public Long findGroupTotal(String packageName) {
        return repository.findGroupTotalTime(packageName);
    }
}
