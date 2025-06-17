package com.taskflow.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotNull(message = "태스크 ID는 필수입니다.")
    private Long taskId;

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    public CommentRequestDto(Long taskId, String content) {
        this.taskId = taskId;
        this.content = content;
    }
}
