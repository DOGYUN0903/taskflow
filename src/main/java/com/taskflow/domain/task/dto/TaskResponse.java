package com.taskflow.domain.task.dto;

import com.taskflow.domain.member.dto.MemberInfoResponse;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 목록 조회 시 사용되는 응답 DTO
 * 일정의 요약 정보와 담당자 정보를 포함
 */
@Getter
public class TaskResponse {

    /** 일정 ID */
    private Long id;

    /** 일정 제목 */
    private String title;

    /** 일정 설명 */
    private String description;

    /** 일정 마감일 */
    private LocalDateTime dueDate;

    /** 일정 우선순위 */
    private TaskPriority priority;

    /** 일정 상태 (TODO, IN_PROGRESS, DONE 등) */
    private TaskStatus status;

    /** 담당자 ID */
    private Long assigneeId;

    /** 담당자 정보 (이름, 이메일 등) */
    private MemberInfoResponse assignee;

    /** 일정 생성일 */
    private LocalDateTime createdAt;

    /** 일정 마지막 수정일 */
    private LocalDateTime updatedAt;

    /**
     * Task 엔티티를 기반으로 DTO를 생성하는 생성자
     *
     * @param task Task 엔티티
     */
    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.priority = task.getPriority();
        this.status = task.getStatus();
        this.assigneeId = task.getAssignee().getId();
        this.assignee = new MemberInfoResponse(task.getAssignee());
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
