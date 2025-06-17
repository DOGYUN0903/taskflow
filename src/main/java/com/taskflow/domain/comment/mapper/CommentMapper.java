package com.taskflow.domain.comment.mapper;

import com.taskflow.domain.comment.dto.CommentResponseDto;
import com.taskflow.domain.comment.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    // 단일 Comment -> ResponseDto
    public CommentResponseDto toResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getTaskId(),
                comment.getMember().getName(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    // List<Comment> → List<CommentResponseDto>
    public List<CommentResponseDto> toResponseDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
