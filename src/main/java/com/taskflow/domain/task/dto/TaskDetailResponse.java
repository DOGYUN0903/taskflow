package com.taskflow.domain.task.dto;

import com.taskflow.domain.member.dto.MemberInfoResponse;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskDetailResponse {

    private Long id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDateTime dueDate;

    private Long assigneeId;
    private MemberInfoResponse assignee;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskDetailResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.status = task.getStatus();
        this.dueDate = task.getDueDate();
        this.assigneeId = task.getManager().getId(); // 연관된 Member 객체에서 ID 추출
        this.assignee = new MemberInfoResponse(task.getManager()); // 전체 정보 DTO로 구성
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
