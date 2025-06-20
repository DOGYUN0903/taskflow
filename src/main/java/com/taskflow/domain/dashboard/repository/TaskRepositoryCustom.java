package com.taskflow.domain.dashboard.repository;


import com.taskflow.domain.dashboard.dto.TaskStatisticsResponse;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;

import java.util.List;

public interface TaskRepositoryCustom {
    TaskStatisticsResponse fetchTaskStatistics();
    List<Task> findTodayTasksByStatusAndAssignee(TaskStatus status, Long assigneeId);

}
