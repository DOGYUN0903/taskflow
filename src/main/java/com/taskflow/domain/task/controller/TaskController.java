package com.taskflow.domain.task.controller;

import com.taskflow.domain.task.dto.TaskCreateRequest;
import com.taskflow.domain.task.dto.TaskUpdateRequest;
import com.taskflow.domain.task.dto.TaskResponse;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.service.TaskService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.response.success.TaskSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * 일정 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createTask(@RequestBody @Valid TaskCreateRequest request,
                                                        @RequestParam String creatorEmail) {
        taskService.createTask(request, creatorEmail);
        return ResponseEntity
                .status(TaskSuccess.TASK_CREATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_CREATED_SUCCESS.getMessage()));
    }

    /**
     * 칸반 보드용 일정 목록 조회 (상태별 그룹핑)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Map<TaskStatus, List<TaskResponse>>>> getTasksGroupedByStatus() {
        Map<TaskStatus, List<TaskResponse>> kanbanData = taskService.getTasksByStatusGrouped();
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_SUCCESS.getMessage(), kanbanData));
    }

    /**
     * 일정 단건 조회
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long taskId) {
        TaskResponse response = taskService.getTaskById(taskId);
        return ResponseEntity
                .status(TaskSuccess.TASK_READ_ONE_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_READ_ONE_SUCCESS.getMessage(), response));
    }

    /**
     * 일정 수정
     */
    @PatchMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> updateTask(@PathVariable Long taskId,
                                                        @RequestBody @Valid TaskUpdateRequest request,
                                                        @RequestParam String requesterEmail) {
        taskService.updateTask(taskId, request, requesterEmail);
        return ResponseEntity
                .status(TaskSuccess.TASK_UPDATED_SUCCESS.getStatus())
                .body(ApiResponse.success(TaskSuccess.TASK_UPDATED_SUCCESS.getMessage()));
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


/**
 * 일정 제목 키워드 조회 (GET + 페이징)
 * 추후 검색 기능
 */
    /*
    @GetMapping(params = {"pageNumber", "size", "title"})
    public ResponseEntity<List<TaskResponse>> getTasksByTitle(@RequestParam int pageNumber,
                                                              @RequestParam int size,
                                                              @RequestParam String title) {
        var page = taskService.getTasksByTitle(title, pageNumber, size);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(page.getContent());
    }

    */

/**
 * 일정 설명 키워드 조회 (POST + 페이징)
 * 추후 검색 기능
 */
    /*
    @PostMapping(params = {"pageNumber", "size"})
    public ResponseEntity<List<TaskResponse>> getTasksByDescription(@RequestBody Map<String, String> body,
                                                                    @RequestParam int pageNumber,
                                                                    @RequestParam int size) {
        String description = body.get("description");
        var page = taskService.getTasksByDescription(description, pageNumber, size);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(page.getContent());
    }
    */