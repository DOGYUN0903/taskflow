package com.taskflow.domain.task.entity;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 일정(Task) 도메인 엔티티
 * 업무 제목, 설명, 상태, 우선순위, 생성자 및 담당자 등 핵심 정보를 포함
 */
@Entity
@Getter
@NoArgsConstructor
public class Task {

    /**
     * 일정 고유 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 우선순위 (LOW, MEDIUM, HIGH)
     */
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    /**
     * 상태 (TODO, IN_PROGRESS, DONE 등)
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /**
     * 시작일 (선택값)
     */
    private LocalDateTime startDate;

    /**
     * 마감일
     */
    private LocalDateTime dueDate;

    /**
     * 생성일
     */
    private LocalDateTime createdAt;

    /**
     * 수정일
     */
    private LocalDateTime updatedAt;

    /**
     * 삭제 여부 플래그
     */
    private boolean isDeleted;

    /**
     * 삭제일시
     */
    private LocalDateTime deletedAt;

    /**
     * 일정 생성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Member creator;

    /**
     * 일정 담당자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Member assignee;

    /**
     * 일정 생성자
     */
    public Task(String title, String description, TaskPriority priority, TaskStatus status,
                LocalDateTime dueDate, Member creator, Member assignee) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.creator = creator;
        this.assignee = assignee;
        this.isDeleted = false;
        this.startDate = null;
        this.deletedAt = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 일정 정보 수정
     */
    public void update(String title, String description, TaskPriority priority, TaskStatus status,
                       LocalDateTime dueDate, Member assignee) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 일정 상태 변경
     */
    public void changeStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 태스크의 시작일(startDate)을 설정하는 메서드
     * 상태가 IN_PROGRESS로 변경될 때 호출됨
     *
     * @param startDate 작업 시작일
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * 일정 삭제 처리
     */
    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

}
