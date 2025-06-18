package com.taskflow.domain.task.controller;

import com.taskflow.domain.task.dto.*;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.service.TaskService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.response.success.TaskSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * 일정 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TaskDetailResponse>> createTask(
            @RequestBody @Valid TaskCreateRequest request,
            @RequestParam String creatorEmail) {

        TaskDetailResponse response = taskService.createTask(request, creatorEmail);

        return ResponseEntity
                .status(TaskSuccess.TASK_CREATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_CREATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 전체 조회 (검색/필터/페이징)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasks(@RequestParam(required = false) TaskStatus status,
                                                                    @RequestParam(required = false) Integer page,
                                                                    @RequestParam(required = false) Integer size,
                                                                    @RequestParam(required = false) String search,
                                                                    @RequestParam(required = false) Long assigneeId) {
        List<TaskResponse> responseList = taskService.getTasks(status, page, size, search, assigneeId);
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_SUCCESS.getMessage(), responseList));
    }

    /**
     * 일정 단건 조회
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> getTaskById(@PathVariable Long taskId) {
        TaskDetailResponse response = taskService.getTaskById(taskId);
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_ONE_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_ONE_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 수정
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> updateTask(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskUpdateRequest request,
            @RequestParam String requesterEmail) {

        TaskDetailResponse response = taskService.updateTask(taskId, request, requesterEmail);

        return ResponseEntity
                .status(TaskSuccess.TASK_UPDATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_UPDATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 상태만 업데이트
     */
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskStatusUpdateRequest request) {

        TaskDetailResponse response = taskService.updateTaskStatus(taskId, request.getStatus());

        return ResponseEntity
                .status(TaskSuccess.TASK_UPDATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_UPDATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 삭제
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity
                .status(TaskSuccess.TASK_DELETED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_DELETED_SUCCESS.getMessage()));
    }
}
