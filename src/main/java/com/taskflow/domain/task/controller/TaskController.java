package com.taskflow.domain.task.controller;

import com.taskflow.domain.task.dto.TaskCreateRequest;
import com.taskflow.domain.task.dto.TaskUpdateRequest;
import com.taskflow.domain.task.dto.TaskResponse;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createTask(@RequestBody TaskCreateRequest request,
                                             @RequestParam String creatorEmail) {
        taskService.createTask(request, creatorEmail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("일정 생성에 성공했습니다.");
    }

    /**
     * 칸반 보드용 일정 목록 조회 (상태별 그룹핑)
     * - 할 일(TODO), 진행 중(IN_PROGRESS), 완료(DONE)
     */
    @GetMapping
    public ResponseEntity<Map<TaskStatus, List<TaskResponse>>> getTasksGroupedByStatus() {
        Map<TaskStatus, List<TaskResponse>> kanbanData = taskService.getTasksByStatusGrouped();
        return ResponseEntity.ok(kanbanData);
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


    /**
     * 일정 수정
     */
    @PatchMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId,
                                             @RequestBody TaskUpdateRequest request,
                                             @RequestParam String requesterEmail) {
        taskService.updateTask(taskId, request, requesterEmail);
        return ResponseEntity.ok("일정 수정에 성공했습니다.");
    }

    /**
     * 일정 삭제
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("일정 삭제에 성공했습니다.");
    }
}
