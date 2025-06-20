package com.taskflow.domain.task.repository;

import com.taskflow.domain.dashboard.repository.TaskRepositoryCustom;
import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Task 엔티티에 대한 데이터베이스 접근을 담당하는 JPA 레포지토리 인터페이스
 */
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {

    /**
     * 삭제되지 않은 전체 일정 조회
     */
    List<Task> findAllByIsDeletedFalse();

    /**
     * 담당자 기준으로 삭제되지 않은 일정 조회
     */
    List<Task> findAllByAssigneeAndIsDeletedFalse(Member assignee);

    /**
     * 작성자 기준으로 삭제되지 않은 일정 조회
     */
    List<Task> findAllByCreatorAndIsDeletedFalse(Member creator);

    /**
     * 상태 기준으로 삭제되지 않은 일정 조회
     */
    List<Task> findAllByStatusAndIsDeletedFalse(TaskStatus status);

    /**
     * 제목 키워드로 삭제되지 않은 일정 검색 (페이징 처리)
     */
    Page<Task> findByTitleContainingAndIsDeletedFalse(String title, Pageable pageable);

    /**
     * 설명 키워드로 삭제되지 않은 일정 검색 (페이징 처리)
     */
    Page<Task> findByDescriptionContainingAndIsDeletedFalse(String description, Pageable pageable);

    /**
     * 제목과 상태 기준으로 삭제되지 않은 일정 조회
     */
    List<Task> findByTitleContainingAndStatusAndIsDeletedFalse(String title, TaskStatus status);

    /**
     * 삭제되지 않은 일정 단건 조회
     */
    Optional<Task> findByIdAndIsDeletedFalse(Long taskId);

    /**
     * 상태, 담당자, 제목 키워드 기준으로 삭제되지 않은 일정 조회
     */
    List<Task> findAllByStatusAndAssigneeAndTitleContainingAndIsDeletedFalse(TaskStatus status, Member assignee, String title);
}
