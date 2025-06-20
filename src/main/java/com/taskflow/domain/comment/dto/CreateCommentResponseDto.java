package com.taskflow.domain.comment.dto;

import com.taskflow.domain.comment.entity.Comment;
import com.taskflow.domain.member.dto.MemberInfoResponse;
import com.taskflow.global.common.HasId;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponseDto implements HasId {

    private Long id;
    private String content;
    private Long taskId;
    private Long userId;
    private MemberInfoResponse user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public CreateCommentResponseDto(Comment comment, MemberInfoResponse memberInfoResponse, Long getTaskId) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.taskId = getTaskId;
        this.userId = memberInfoResponse.getId();
        this.user = memberInfoResponse;
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
    /*
    public CommentResponseDto(Comment comment, Long taskId) {
        this.id = comment.getId();
        this.taskId = taskId;
        this.memberName = comment.getMember().getName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

     */

    @Override
    public Long getId() {
        return id;
    }
}
