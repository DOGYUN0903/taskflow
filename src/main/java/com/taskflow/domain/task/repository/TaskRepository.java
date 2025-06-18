package com.taskflow.domain.task.repository;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // 기본 조회
    List<Task> findAllByIsDeletedFalse();

    // 담당자 기준
    List<Task> findAllByManagerAndIsDeletedFalse(Member manager);

    // 작성자 기준
    List<Task> findAllByCreatorAndIsDeletedFalse(Member creator);

    // 상태 기준
    List<Task> findAllByStatusAndIsDeletedFalse(TaskStatus status);

    // 제목 키워드 검색 (페이징)
    Page<Task> findByTitleContainingAndIsDeletedFalse(String title, Pageable pageable);

    // 설명 키워드 검색 (페이징)
    Page<Task> findByDescriptionContainingAndIsDeletedFalse(String description, Pageable pageable);

    // 제목 + 상태
    List<Task> findByTitleContainingAndStatusAndIsDeletedFalse(String title, TaskStatus status);

    // 단건 조회 시 삭제된 건 제외
    Optional<Task> findByIdAndIsDeletedFalse(Long taskId);

    // 상태 + 담당자 + 검색
    List<Task> findAllByStatusAndManagerAndTitleContainingAndIsDeletedFalse(TaskStatus status, Member manager, String title);
}
