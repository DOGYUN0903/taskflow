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
 * TaskController는 일정(Task) 관련 HTTP 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * 새로운 일정을 생성하는 API
     *
     * @param request     일정 생성 요청 DTO
     * @param userDetails 인증된 사용자 정보
     * @return 생성된 일정 상세 응답 DTO
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
     * 일정 목록을 조회하는 API
     * 상태, 키워드, 담당자 ID 기준 필터링 및 페이징 지원
     *
     * @param status     일정 상태 필터 (예: TODO, IN_PROGRESS)
     * @param page       페이지 번호
     * @param size       페이지 크기
     * @param search     제목 또는 설명 키워드
     * @param assigneeId 담당자 ID
     * @return 일정 목록 및 페이징 응답 DTO
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
     * 일정 단건을 조회하는 API
     *
     * @param taskId 조회할 일정 ID
     * @return 일정 상세 응답 DTO
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> getTaskById(@PathVariable Long taskId) {
        TaskDetailResponse response = taskService.getTaskById(taskId);
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_ONE_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_ONE_SUCCESS.getMessage(), response));
    }

    /**
     * 일정을 수정하는 API
     * 제목, 설명, 마감일, 우선순위, 상태, 담당자 정보 수정 가능
     *
     * @param taskId            수정 대상 일정 ID
     * @param request           일정 수정 요청 DTO
     * @param customUserDetails 인증된 사용자 정보
     * @return 수정된 일정 상세 응답 DTO
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
     * 일정 상태만 변경하는 API
     * 상태 전이 순서 (TODO → IN_PROGRESS → DONE) 검증 포함
     *
     * @param taskId      대상 일정 ID
     * @param request     상태 변경 요청 DTO
     * @param customUserDetails 인증된 사용자 정보
     * @return 상태가 변경된 일정 상세 응답 DTO
     */
    @LogActivity(value = ActivityType.TASK_STATUS_CHANGED, target = "taskId")
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskStatusUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        TaskDetailResponse response = taskService.updateTaskStatus(taskId, request.getStatus(), customUserDetails.getId());

        return ResponseEntity
                .status(TaskSuccess.TASK_STATUS_UPDATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_STATUS_UPDATED_SUCCESS.getMessage(), response));
    }

    /**
     * 일정을 삭제하는 API (Soft Delete 방식)
     *
     * @param taskId 삭제 대상 일정 ID
     * @return 삭제 성공 메시지를 포함한 응답
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
