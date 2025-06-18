package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 일정 생성 요청을 처리하는 DTO
 * 클라이언트로부터 일정 생성 시 필요한 데이터를 수신
 */
@Setter
@Getter
@NoArgsConstructor
public class TaskCreateRequest {

    /**
     * 일정 제목 (필수)
     */
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    /**
     * 일정 설명 (선택)
     */
    private String description;

    /**
     * 일정 우선순위 (필수)
     */
    @NotNull(message = "우선순위는 필수입니다.")
    private TaskPriority priority;

    /**
     * 담당자 ID (필수)
     */
    @NotNull(message = "담당자 ID는 필수입니다.")
    private Long assigneeId;

    /**
     * 마감일 (현재 시간보다 이후여야 함)
     */
    @Future(message = "마감일은 현재보다 이후여야 합니다.")
    private LocalDateTime dueDate;
}
