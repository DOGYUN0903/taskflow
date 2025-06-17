package com.taskflow.domain.comment.controller;

import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CommentResponseDto;
import com.taskflow.domain.comment.service.CommentService;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.response.success.CommentSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createCommentAPI(
            @RequestBody @Valid CommentRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        commentService.createComment(requestDto, userDetails.getUsername());
        return ResponseEntity
                .status(CommentSuccess.COMMENT_CREATED.getStatus())
                .body(ApiResponse.success(CommentSuccess.COMMENT_CREATED.getMessage()));
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateCommentAPI(
            @PathVariable Long id,
            @RequestBody @Valid CommentRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CommentResponseDto responseDto = commentService.updateComment(
                id, requestDto, userDetails.getUsername()
        );
        return ResponseEntity
                .status(CommentSuccess.COMMENT_UPDATED.getStatus())
                .body(ApiResponse.success(CommentSuccess.COMMENT_UPDATED.getMessage(), responseDto));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCommentAPI(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.deleteComment(id, userDetails.getUsername());
        return ResponseEntity
                .status(CommentSuccess.COMMENT_DELETED.getStatus())
                .body(ApiResponse.success(CommentSuccess.COMMENT_DELETED.getMessage()));
    }

    // 특정 태스크의 댓글 목록 조회(최신순)


    // 댓글 내용 Like 검색

}
