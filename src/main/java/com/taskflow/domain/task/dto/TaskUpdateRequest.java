package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 수정 요청을 처리하기 위한 DTO
 * 클라이언트로부터 일정 수정 정보 수신
 */
@Getter
public class TaskUpdateRequest {

    /**
     * 일정 제목
     */
    @NotBlank(message = "제목은 필수 입력해주세요.")
    private String title;

    /**
     * 일정 설명
     */
    private String description;

    /**
     * 우선순위
     */
    @NotNull(message = "우선순위를 선택해주세요.")
    private TaskPriority priority;

    /**
     * 담당자 ID
     */
    @NotNull(message = "담당자 ID는 필수입니다.")
    private Long assigneeId;

    /**
     * 일정 상태
     */
    //@NotNull(message = "상태를 선택해주세요.")
    private TaskStatus status;

    /**
     * 시작일 (선택값)
     */
    private LocalDateTime startDate;

    /**
     * 마감일
     */
    @Future(message = "마감일은 현재보다 이후여야 합니다.")
    @NotNull(message = "마감일은 필수로 지정해야 합니다.")
    private LocalDateTime dueDate;
}
