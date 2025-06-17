package com.taskflow.domain.comment.repository;

import com.taskflow.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 태스크에 달린 댓글(최신순 - 삭제되지 않은 것만)
    List<Comment> findAllByTaskId(Long taskId);

    // 댓글 내용에 대한 Like 검색
    List<Comment> findByContentIsLike(String keyword);

}
