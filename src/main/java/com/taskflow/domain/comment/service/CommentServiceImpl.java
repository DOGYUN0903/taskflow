package com.taskflow.domain.comment.service;

import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CreateCommentResponseDto;
import com.taskflow.domain.comment.dto.SearchCommentResponseDto;
import com.taskflow.domain.comment.entity.Comment;
import com.taskflow.domain.comment.repository.CommentRepository;
import com.taskflow.domain.member.dto.MemberInfoResponse;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.exception.MemberNotFoundException;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.exception.TaskNotFoundException;
import com.taskflow.domain.task.repository.TaskRepository;
import com.taskflow.global.exception.comment.CommentNotFoundException;
import com.taskflow.global.exception.comment.UnauthorizedCommentAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;

    // 댓글 작성
    @Override
    @Transactional
    public CreateCommentResponseDto createComment(Long taskId, CommentRequestDto requestDto, String email) {
        System.out.println("==============================");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new MemberNotFoundException());

        Task task = taskRepository.findById(taskId)
                .orElseThrow(()->new TaskNotFoundException());

        String content = requestDto.getContent();

        Comment comment = new Comment(content, member, task);

        commentRepository.save(comment);

        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(member);

        CreateCommentResponseDto commentResponseDto = new CreateCommentResponseDto(comment, memberInfoResponse, taskId);

        return commentResponseDto;

    }

    // 댓글 목록 조회
    @Override
    @Transactional(readOnly = true)
    public SearchCommentResponseDto getCommentsByTaskId(Long taskId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> pageComment = commentRepository
                .findAllByTaskIdAndIsDeletedOrderByCreatedAtDesc(taskId, false, pageable);

        List<SearchCommentResponseDto.Content> contentList = pageComment.getContent().stream().map(comment -> new SearchCommentResponseDto.Content(comment, new MemberInfoResponse(comment.getMember()), taskId)).toList();

        SearchCommentResponseDto searchCommentResponseDto = new SearchCommentResponseDto(contentList, pageComment, size, page);
        return searchCommentResponseDto;
    }

    // 댓글 삭제
    @Override
    @Transactional
    public void deleteComment(Long id, String email, Long taskId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        //인증 확인
        if(!comment.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedCommentAccessException();
        }
        //taskId 경로 확인=> 잘못된 taskId로 접근 또는 이미 삭제된 댓글일 경우 댓글 못 찾는 예외 처리
        if(!taskId.equals(comment.getTask().getId()) || comment.isDeleted())
            throw new CommentNotFoundException();

        comment.softDelete();
    }

    // 댓글 수정
    /*
    // 댓글 수정
    @Override
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedCommentAccessException();
        }

        comment.updateContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }
     */

    // 댓글 검색
    /*
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> searchComments(String keyword) {
        return commentRepository.findByContentIsLike(keyword).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

     */
}
