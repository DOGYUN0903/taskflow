package com.taskflow.domain.task.dto;

import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 조회 응답을 위한 DTO 클래스
 * 클라이언트에게 일정 정보를 반환할 때 사용
 */
@Getter
@Builder
public class TaskResponse {

    private String title;
    private String description;
    private TaskPriority priority;
    private String managerName;
    private String creatorName;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private LocalDateTime startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Task 엔티티를 TaskResponse DTO로 변환하는 정적 팩토리 메서드
     *
     * @param task 변환할 Task 엔티티
     * @return TaskResponse DTO
     */
    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .managerName(task.getManager().getName())  // Member에서 이름 추출
                .creatorName(task.getCreator().getName())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .startDate(task.getStartDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
