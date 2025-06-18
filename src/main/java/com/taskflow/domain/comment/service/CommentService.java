package com.taskflow.domain.comment.service;

import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(Long taskId, CommentRequestDto requestDto, String email);
    /*
    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, String email);
    */

    void deleteComment(Long id, String email);

    List<CommentResponseDto> getCommentsByTaskId(Long taskId, int page, int size);

    List<CommentResponseDto> searchComments(String keyword);

}
