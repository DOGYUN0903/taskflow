package com.taskflow.domain.task.repository;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code TaskRepository}는 Task 엔티티에 대한 데이터베이스 접근을 처리하는 인터페이스
 * Spring Data JPA를 통해 기본 CRUD 기능을 자동으로 제공
 *
 * <p>추가적으로 필요한 메서드:
 * <ul>
 *     <li>특정 작성자 또는 담당자의 일정 목록 조회</li>
 *     <li>제목, 설명 키워드 검색 (페이징)</li>
 *     <li>삭제되지 않은 일정만 필터링</li>
 * </ul>
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * 삭제되지 않은 전체 일정 목록을 반환
     *
     * @return 삭제되지 않은 Task 리스트
     */
    List<Task> findAllByIsDeletedFalse();

    /**
     * 특정 담당자의 삭제되지 않은 일정 목록을 반환
     *
     * @param manager 담당자 Member 객체
     * @return 해당 담당자의 Task 리스트
     */
    List<Task> findAllByManagerAndIsDeletedFalse(Member manager);

    /**
     * 특정 작성자의 삭제되지 않은 일정 목록을 반환
     *
     * @param creator 작성자 Member 객체
     * @return 해당 작성자의 Task 리스트
     */
    List<Task> findAllByCreatorAndIsDeletedFalse(Member creator);

    /**
     * 제목 키워드를 포함하고 삭제되지 않은 일정 목록을 페이징으로 반환
     *
     * @param title 제목 키워드
     * @param pageable 페이징 정보
     * @return 페이징된 Task 리스트
     */
    Page<Task> findByTitleContainingAndIsDeletedFalse(String title, Pageable pageable);

    /**
     * 설명 키워드를 포함하고 삭제되지 않은 일정 목록을 페이징으로 반환
     *
     * @param description 설명 키워드
     * @param pageable 페이징 정보
     * @return 페이징된 Task 리스트
     */
    Page<Task> findByDescriptionContainingAndIsDeletedFalse(String description, Pageable pageable);
}
