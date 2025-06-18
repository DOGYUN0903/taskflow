package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 일정 상태 변경 요청 DTO
 */
@Getter
public class TaskStatusUpdateRequest {

    @NotNull(message = "상태 값은 필수입니다.")
    private TaskStatus status;
}
