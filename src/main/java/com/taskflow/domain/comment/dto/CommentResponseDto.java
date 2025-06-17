package com.taskflow.domain.comment.dto;

import com.taskflow.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long taskId;
    private String memberName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Long taskId, String memberName, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.taskId = taskId;
        this.memberName = memberName;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
