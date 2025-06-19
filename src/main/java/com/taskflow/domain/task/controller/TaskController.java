package com.taskflow.domain.task.controller;

import com.taskflow.domain.activitylog.entity.ActivityType;
import com.taskflow.domain.task.dto.*;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.service.TaskService;
import com.taskflow.global.annotation.LogActivity;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.response.success.TaskSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 일정(Task) 관련 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * 새로운 일정을 생성합니다.
     *
     * @param request       일정 생성 요청 DTO
     * @param userDetails   로그인한 사용자 정보
     * @return 생성된 일정 정보가 포함된 응답
     */
    @LogActivity(ActivityType.TASK_CREATED)
    @PostMapping
    public ResponseEntity<ApiResponse<TaskDetailResponse>> createTask(
            @RequestBody @Valid TaskCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        TaskDetailResponse response = taskService.createTask(request, userDetails.getId());

        return ResponseEntity
                .status(TaskSuccess.TASK_CREATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_CREATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 목록을 조회합니다. (필터, 검색, 페이징 지원)
     *
     * @param status      일정 상태 (예: TODO, IN_PROGRESS)
     * @param page        페이지 번호
     * @param size        페이지 크기
     * @param search      검색 키워드
     * @param assigneeId  담당자 ID
     * @return 일정 목록 및 페이징 정보
     */
    @GetMapping
    public ResponseEntity<ApiResponse<TaskPageResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long assigneeId) {

        TaskPageResponse response = taskService.getTasks(status, page, size, search, assigneeId);
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 단건을 조회합니다.
     *
     * @param taskId 조회할 일정의 ID
     * @return 일정 상세 정보
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> getTaskById(@PathVariable Long taskId) {
        TaskDetailResponse response = taskService.getTaskById(taskId);
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_ONE_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_ONE_SUCCESS.getMessage(), response));
    }

    /**
     * 일정을 수정합니다.
     *
     * @param taskId             수정할 일정의 ID
     * @param request            일정 수정 요청 DTO
     * @param customUserDetails  로그인한 사용자 정보
     * @return 수정된 일정 정보 응답
     */
    @LogActivity(value = ActivityType.TASK_UPDATED, target = "taskId")
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> updateTask(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        TaskDetailResponse response = taskService.updateTask(taskId, request, customUserDetails.getId());

        return ResponseEntity
                .status(TaskSuccess.TASK_UPDATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_UPDATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 상태만 업데이트합니다.
     *
     * @param taskId 일정 ID
     * @param request 상태 변경 요청 DTO
     * @return 상태가 변경된 일정 정보
     */
    @LogActivity(value = ActivityType.TASK_STATUS_CHANGED, target = "taskId")
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskStatusUpdateRequest request) {

        TaskDetailResponse response = taskService.updateTaskStatus(taskId, request.getStatus());

        return ResponseEntity
                .status(TaskSuccess.TASK_STATUS_UPDATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_STATUS_UPDATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정을 삭제합니다.
     *
     * @param taskId 삭제할 일정 ID
     * @return 성공 응답 메시지
     */
    @LogActivity(value = ActivityType.TASK_DELETED, target = "taskId")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity
                .status(TaskSuccess.TASK_DELETED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_DELETED_SUCCESS.getMessage()));
    }
}
