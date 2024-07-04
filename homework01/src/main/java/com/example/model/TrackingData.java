package com.example.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class represents data to be tracked
 */
public class TrackingData {
    private long id;
    private LocalDateTime executedAt;
    private String packageName;
    private String methodName;
    private long executionTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingData that = (TrackingData) o;
        return id == that.id && executionTime == that.executionTime
                && Objects.equals(executedAt, that.executedAt)
                && Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executedAt, methodName, executionTime);
    }

    @Override
    public String toString() {
        return "TrackingData{" +
                "id=" + id +
                ", executedAt=" + executedAt +
                ", methodName='" + methodName + '\'' +
                ", executionTime=" + executionTime +
                '}';
    }
}
