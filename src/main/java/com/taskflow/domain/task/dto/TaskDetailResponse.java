package com.taskflow.domain.task.dto;

import com.taskflow.domain.member.dto.MemberInfoResponse;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정(Task) 상세 정보를 응답하는 DTO
 * 단건 조회 또는 생성/수정 이후 상세 내용을 포함
 */
@Getter
public class TaskDetailResponse {

    /**
     * 일정 ID
     */
    private Long id;

    /**
     * 일정 제목
     */
    private String title;

    /**
     * 일정 설명
     */
    private String description;

    /**
     * 일정 우선순위
     */
    private TaskPriority priority;

    /**
     * 일정 상태
     */
    private TaskStatus status;

    /**
     * 일정 마감일
     */
    private LocalDateTime dueDate;

    /**
     * 담당자 ID
     */
    private Long assigneeId;

    /**
     * 담당자 상세 정보
     */
    private MemberInfoResponse assignee;

    /**
     * 생성일
     */
    private LocalDateTime createdAt;

    /**
     * 수정일
     */
    private LocalDateTime updatedAt;

    /**
     * Task 엔티티 기반으로 응답 객체 생성
     * @param task Task 엔티티 객체
     */
    public TaskDetailResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.status = task.getStatus();
        this.dueDate = task.getDueDate();
        this.assigneeId = task.getAssignee().getId();
        this.assignee = new MemberInfoResponse(task.getAssignee());
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
