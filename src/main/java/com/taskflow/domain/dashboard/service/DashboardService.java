package com.taskflow.domain.dashboard.service;

import com.taskflow.domain.dashboard.dto.TaskSimpleResponse;
import com.taskflow.domain.dashboard.dto.TaskStatisticsResponse;
import com.taskflow.domain.dashboard.dto.TodayTaskSummaryResponse;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.repository.TaskRepository;
import com.taskflow.global.exception.dashboard.StastisticException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;

    public DashboardService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskStatisticsResponse getTaskStatistics() {

        TaskStatisticsResponse dto = taskRepository.fetchTaskStatistics();

        if (dto.totalCount() == 0) {
            throw new StastisticException("태스크 통계를 불러오는 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return dto;
    }

    public TodayTaskSummaryResponse getTodayTaskSummary(Long assigneeId) {
        List<Task> todos = taskRepository.findTodayTasksByStatusAndAssignee(TaskStatus.TODO, assigneeId);
        List<Task> inProgress = taskRepository.findTodayTasksByStatusAndAssignee(TaskStatus.IN_PROGRESS, assigneeId);

        return new TodayTaskSummaryResponse(
                todos.stream().map(TaskSimpleResponse::from).toList(),
                inProgress.stream().map(TaskSimpleResponse::from).toList()
        );
    }

}
