package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Getter;

/**
 * 일정 생성 요청을 위한 DTO 클래스
 * 클라이언트로부터 일정 생성 요청 데이터를 수신
 */
@Getter
public class TaskCreateRequest {

    private String title;
    private String description;
    private TaskPriority priority;
    private String managerName;
    private TaskStatus status;
}
