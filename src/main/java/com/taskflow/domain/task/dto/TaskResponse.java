package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 조회 응답을 위한 DTO 클래스
 * 클라이언트에게 일정 정보를 반환할 때 사용
 */
@Getter
@Builder
public class TaskResponse {

    private String title;
    private String description;
    private TaskPriority priority;
    private String managerName;
    private String creatorName;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private LocalDateTime startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
