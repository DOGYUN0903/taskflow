package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 생성 요청을 위한 DTO 클래스
 * 클라이언트로부터 일정 생성 요청 데이터를 수신
 */
@Getter
public class TaskCreateRequest {

    @NotBlank(message = "제목은 필수 입력해주세요.")
    private String title;
    private String description;
    private TaskPriority priority;
    @NotBlank(message = "담당자 지정은 필수입니다.")
    private String managerName;

    private TaskStatus status;

    private LocalDateTime startDate;
    @NotNull(message = "마감일은 필수로 지정해야 합니다.")
    private LocalDateTime dueDate;
}
