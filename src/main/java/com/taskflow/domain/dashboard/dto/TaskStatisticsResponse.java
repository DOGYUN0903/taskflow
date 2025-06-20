package com.taskflow.domain.dashboard.dto;

public record TaskStatisticsResponse(
        long totalCount,
        long todoCount,
        long inProgressCount,
        long doneCount,
        double completionRate,
        long overdueCount
) {}
