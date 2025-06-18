package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 일정 상태 변경 요청을 처리하기 위한 DTO
 * 예: TODO → IN_PROGRESS, DONE 등 상태 변경 시 사용
 */
@Getter
public class TaskStatusUpdateRequest {

    /**
     * 새로운 상태값 (필수 입력)
     * 예: TODO, IN_PROGRESS, DONE
     */
    @NotNull(message = "상태 값은 필수입니다.")
    private TaskStatus status;
}
