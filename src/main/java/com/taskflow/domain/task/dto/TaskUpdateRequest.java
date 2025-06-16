package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 수정 요청을 위한 DTO 클래스
 * 클라이언트로부터 일정 수정 요청 데이터를 수신
 */
@Getter
public class TaskUpdateRequest {

    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private String managerName;

    private LocalDateTime startDate;
    private LocalDateTime dueDate;
}
