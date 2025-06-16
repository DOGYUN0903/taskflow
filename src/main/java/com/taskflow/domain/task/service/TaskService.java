package com.taskflow.domain.task.service;

import com.taskflow.domain.task.dto.TaskCreateRequest;
import com.taskflow.domain.task.dto.TaskResponse;
import com.taskflow.domain.task.dto.TaskUpdateRequest;

import java.util.List;

/**
 * 일정 로직을 정의한 서비스 인터페이스
 */
public interface TaskService {

    /**
     * 일정 생성
     *
     * @param request 일정 생성 요청 DTO
     */
    void createTask(TaskCreateRequest request);

    /**
     * 일정 제목 키워드로 조회
     *
     * @param pageNumber 페이지 번호
     * @param size 한 페이지에 조회할 개수
     * @param title 제목 키워드
     * @return 일정 응답 리스트
     */
    List<TaskResponse> getTasksByTitle(int pageNumber, int size, String title);

    /**
     * 일정 내용을 키워드로 조회
     *
     * @param pageNumber 페이지 번호
     * @param size 한 페이지에 조회할 개수
     * @param description 설명 키워드
     * @return 일정 응답 리스트
     */
    List<TaskResponse> getTasksByDescription(int pageNumber, int size, String description);

    /**
     * 일정 수정
     *
     * @param taskId 수정할 일정 ID
     * @param request 수정 요청 DTO
     */
    void updateTask(Long taskId, TaskUpdateRequest request);

    /**
     * 일정 삭제 (Soft Delete)
     *
     * @param taskId 삭제할 일정 ID
     */
    void deleteTask(Long taskId);
}
