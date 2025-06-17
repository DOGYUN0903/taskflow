package com.taskflow.domain.comment.service;

import com.taskflow.domain.comment.dto.CommentRequestDto;
import com.taskflow.domain.comment.dto.CommentResponseDto;
import com.taskflow.domain.comment.entity.Comment;
import com.taskflow.domain.comment.repository.CommentRepository;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.global.exception.comment.CommentNotFoundException;
import com.taskflow.global.exception.comment.UnauthorizedCommentAccessException;
import com.taskflow.global.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    // 댓글 작성
    @Override
    @Transactional
    public CommentResponseDto createComment(Long taskId, CommentRequestDto requestDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new MemberNotFoundException());

        String content = requestDto.getContent();
        Comment comment = new Comment(taskId, content, member);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
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

    // 댓글 삭제
    @Override
    @Transactional
    public void deleteComment(Long id, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedCommentAccessException();
        }

        comment.softDelete();
    }

    // 댓글 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

    }

    // 댓글 검색
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> searchComments(String keyword) {
        return commentRepository.findByContentIsLike(keyword).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
