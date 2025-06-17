package com.taskflow.domain.comment.service;

import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CommentResponseDto;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto requestDto, String email);

    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, String email);

    void deleteComment(Long id, String email);

    List<CommentResponseDto> getCommentsByTaskId(Long taskId);

    List<CommentResponseDto> searchComments(String keyword);

}
