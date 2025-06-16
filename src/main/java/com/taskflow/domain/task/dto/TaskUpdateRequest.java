package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Getter;

/**
 * 일정 수정 요청을 위한 DTO 클래스
 * 클라이언트로부터 수정할 일정 정보를 전달받음
 */
@Getter
public class TaskUpdateRequest {

    private String title;
    private String description;
    private TaskPriority priority;
    private String managerName;
    private TaskStatus status;
}
