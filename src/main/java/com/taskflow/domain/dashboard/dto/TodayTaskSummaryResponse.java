package com.taskflow.domain.dashboard.dto;

import java.util.List;

public record TodayTaskSummaryResponse(
        List<TaskSimpleResponse> todoTasks,
        List<TaskSimpleResponse> inProgressTasks
) {}
