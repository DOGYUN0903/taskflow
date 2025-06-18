package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String description;

    @NotNull(message = "우선순위는 필수입니다.")
    private TaskPriority priority;

    @NotNull(message = "담당자 ID는 필수입니다.")
    private Long assigneeId;

    @Future(message = "마감일은 현재보다 이후여야 합니다.")
    private LocalDateTime dueDate;
}
