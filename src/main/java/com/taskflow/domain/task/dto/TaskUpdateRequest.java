package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 수정 요청을 위한 DTO 클래스
 * 클라이언트로부터 일정 수정 요청 데이터를 수신
 */
@Getter
public class TaskUpdateRequest {

    @NotBlank(message = "제목은 필수 입력해주세요.")
    private String title;

    private String description;

    @NotNull(message = "우선순위를 선택해주세요.")
    private TaskPriority priority;

    @NotNull(message = "담당자 ID는 필수입니다.")
    private Long assigneeId;

    @NotNull(message = "상태를 선택해주세요.")
    private TaskStatus status;

    private LocalDateTime startDate;

    @NotNull(message = "마감일은 필수로 지정해야 합니다.")
    private LocalDateTime dueDate;
}
