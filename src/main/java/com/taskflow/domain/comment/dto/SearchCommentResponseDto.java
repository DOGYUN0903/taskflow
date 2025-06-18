package com.taskflow.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.taskflow.domain.comment.entity.Comment;
import com.taskflow.domain.member.dto.MemberInfoResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SearchCommentResponseDto{
    List<Content> content;
    long totalElements;
    long totalPages;
    int size;
    int number;

    public SearchCommentResponseDto(List<Content> content, Page<Comment> pageComment, int size, int page){
        this.content = content;
        this.totalElements = pageComment.getTotalElements();
        this.totalPages = pageComment.getTotalPages();
        this.size = size;
        this.number = page;

    }
@JsonPropertyOrder({"id", "content", "taskId", "userId", "user", "createdAt", "updatedAt"})
    @Getter
    public static class Content{
        Long id;
        String content;
        Long taskId;
        Long userId;
        MemberInfoResponse user;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;

        public Content(Comment comment, MemberInfoResponse memberInfoResponse, Long taskId){
            this.id = comment.getId();
            this.content = comment.getContent();
            this.taskId = taskId;
            this.userId = memberInfoResponse.getId();
            this.user = memberInfoResponse;
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = comment.getUpdatedAt();

        }
    }
}


