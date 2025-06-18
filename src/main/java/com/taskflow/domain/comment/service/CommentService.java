package com.taskflow.domain.comment.service;

import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CreateCommentResponseDto;
import com.taskflow.domain.comment.dto.SearchCommentResponseDto;

public interface CommentService {
    CreateCommentResponseDto createComment(Long taskId, CommentRequestDto requestDto, String email);
    /*
    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, String email);
    */

    void deleteComment(Long id, String email, Long taskId);

    SearchCommentResponseDto getCommentsByTaskId(Long taskId, int page, int size);

    //List<CommentResponseDto> searchComments(String keyword);

}
