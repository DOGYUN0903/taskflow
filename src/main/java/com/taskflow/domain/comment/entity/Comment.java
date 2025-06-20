package com.taskflow.domain.comment.entity;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    @Column(name = "task_id", nullable = false)
    private Long taskId;

     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /*
    public Comment(Long taskId, String content, Member member) {
        this.taskId = taskId;
        this.content = content;
        this.member = member;
    }

     */

    public Comment(String content, Member member, Task task) {
        this.content = content;
        this.member = member;
        this.task = task;
    }
    /*
    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
     */
    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
