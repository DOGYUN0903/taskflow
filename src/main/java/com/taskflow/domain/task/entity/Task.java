package com.taskflow.domain.task.entity;

import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK (일정 ID)

    private Long creatorId; // 작성자 ID

    private Long managerId; // 담당자 ID

    private String title; // 일정 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 일정 상세 설명

    @Enumerated(EnumType.STRING)
    private TaskPriority priority; // 우선순위 (LOW, MEDIUM, HIGH)

    @Enumerated(EnumType.STRING)
    private TaskStatus status; // 상태 (TODO, IN_PROGRESS, DONE)

    private LocalDateTime dueDate; // 마감일

    private LocalDateTime startDate; // 시작일 (선택값)

    private Boolean isDeleted; // 삭제 여부 (soft delete)

    private LocalDateTime deletedAt; // 삭제된 시간


}
