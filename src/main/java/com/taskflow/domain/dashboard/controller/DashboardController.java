package com.taskflow.domain.dashboard.controller;

import com.taskflow.domain.dashboard.dto.TaskStatisticsResponse;
import com.taskflow.domain.dashboard.dto.TodayTaskSummaryResponse;
import com.taskflow.domain.dashboard.service.DashboardService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.response.success.StatisticSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;


    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    //통계값 조회 API
    @GetMapping("/dashboard/statistics")
    public ResponseEntity<ApiResponse<TaskStatisticsResponse>> getStatistics() {
        return ResponseEntity.status(HttpStatus.OK).
                body(ApiResponse.success(StatisticSuccess.STATISTIC_SUCCESS.getMessage(),dashboardService.getTaskStatistics()));
    }

    //오늘의 태스크 요약
    @GetMapping("/dashboard/my-tasks/today")
    public ResponseEntity<ApiResponse<TodayTaskSummaryResponse>> getMyTodayTasks(@RequestParam Long assigneeId) {
        return ResponseEntity.status(HttpStatus.OK).
//        body(ApiResponse.success(TaskSuccess.TASK_READ_SUCCESS.getMessage(), response));
        body(ApiResponse.success("태스크요약 성공", dashboardService.getTodayTaskSummary(assigneeId)));
    }
}
