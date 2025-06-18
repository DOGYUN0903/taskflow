package com.taskflow.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

}
