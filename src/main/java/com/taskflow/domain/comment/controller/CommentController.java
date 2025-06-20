package com.taskflow.domain.comment.controller;

import com.taskflow.domain.activitylog.entity.ActivityType;
import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CreateCommentResponseDto;
import com.taskflow.domain.comment.dto.SearchCommentResponseDto;
import com.taskflow.domain.comment.service.CommentService;
import com.taskflow.global.annotation.LogActivity;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import com.taskflow.global.response.success.CommentSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @LogActivity(ActivityType.COMMENT_CREATED)
    @PostMapping
    public ResponseEntity<ApiResponse<CreateCommentResponseDto>> createCommentAPI(
            @PathVariable Long taskId,
            @RequestBody @Valid CommentRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        CreateCommentResponseDto responseDto = commentService.createComment(taskId, requestDto, userDetails.getUsername());
        return ResponseEntity
                .status(CommentSuccess.COMMENT_CREATED.getStatus())
                .body(ApiResponse.success(CommentSuccess.COMMENT_CREATED.getMessage(), responseDto));
    }


    // 특정 태스크의 댓글 목록 조회(최신순)
    @GetMapping
    public ResponseEntity<ApiResponse<SearchCommentResponseDto>> readCommentsByTaskAPI(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SearchCommentResponseDto responseDto = commentService.getCommentsByTaskId(taskId, page, size);
        return ResponseEntity.ok(ApiResponse.success(CommentSuccess.COMMENT_READ.getMessage(), responseDto));
    }


    // 댓글 삭제
    @LogActivity(value = ActivityType.COMMENT_DELETED, target = "commentId")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteCommentAPI(
        @PathVariable Long commentId,
        @PathVariable Long taskId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getUsername(), taskId);
        return ResponseEntity
                .status(CommentSuccess.COMMENT_DELETED.getStatus())
                .body(ApiResponse.success(CommentSuccess.COMMENT_DELETED.getMessage()));
    }









//검색
/*
    // 댓글 내용 Like 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> searchCommentsAPI(
            @RequestParam String keyword
    ) {
        List<CommentResponseDto> responseDtoList = commentService.searchComments(keyword);
        return ResponseEntity.ok(ApiResponse.success(CommentSuccess.COMMENT_SEARCH.getMessage(), responseDtoList));
    }

 */

//수정
/*
    // 댓글 수정
    @PutMapping("/{commentId}")
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
    */
}
