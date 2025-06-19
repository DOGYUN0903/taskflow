package com.taskflow.domain.task.service;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.task.dto.*;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.exception.*;
import com.taskflow.domain.task.repository.TaskRepository;
import com.taskflow.global.response.error.TaskError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 일정(Task) 도메인의 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 일정 생성, 조회, 수정, 삭제, 상태 변경 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    /**
     * 일정을 생성합니다.
     *
     * @param request   일정 생성 요청 DTO
     * @param memberId  생성자 회원 ID
     * @return 생성된 일정 상세 정보 DTO
     */
    public TaskDetailResponse createTask(TaskCreateRequest request, Long memberId) {
        Member creator = memberRepository.findById(memberId)
                .orElseThrow(CreatorNotFoundException::new);

        Member assignee = memberRepository.findById(request.getAssigneeId())
                .orElseThrow(AssigneeNotFoundException::new);

        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                TaskStatus.TODO,
                request.getDueDate(),
                creator,
                assignee
        );

        Task saved = taskRepository.save(task);
        return new TaskDetailResponse(saved);
    }

    /**
     * 일정 전체 조회
     * 상태, 키워드, 담당자 ID 필터링 및 페이징 처리 포함
     *
     * @param status 필터링할 일정 상태
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param search 제목 또는 설명 키워드
     * @param assigneeId 담당자 ID
     * @return 페이징된 일정 리스트 응답
     */
    @Transactional(readOnly = true)
    public TaskPageResponse getTasks(TaskStatus status, Integer page, Integer size, String search, Long assigneeId) {
        List<Task> tasks = taskRepository.findAllByIsDeletedFalse();

        // 필터링
        List<Task> filtered = tasks.stream()
                .filter(task -> status == null || task.getStatus() == status)
                .filter(task -> {
                    if (search == null) return true;
                    String keyword = search.toLowerCase();
                    return task.getTitle().toLowerCase().contains(keyword)
                            || task.getDescription().toLowerCase().contains(keyword);
                })
                .filter(task -> assigneeId == null || Objects.equals(task.getAssignee().getId(), assigneeId))
                .collect(Collectors.toList());

        // 결과가 없을 경우 - 일부 조건만 예외 처리
        if (filtered.isEmpty()) {
            if (search != null) {
                throw new TaskNotFoundException(TaskError.TASK_NOT_FOUND_BY_SEARCH);
            }
            if (assigneeId != null) {
                throw new TaskNotFoundException(TaskError.TASK_NOT_FOUND_BY_ASSIGNEE);
            }

            // status만 조건이거나, 아예 조건 없을 경우엔 빈 결과 반환
            return new TaskPageResponse(
                    Collections.emptyList(),
                    0,
                    0,
                    size != null ? size : 0,
                    page != null ? page : 0
            );
        }

        // 페이징 계산
        int start = (page != null && size != null) ? page * size : 0;
        int end = (size != null) ? Math.min(start + size, filtered.size()) : filtered.size();
        List<TaskResponse> paged = filtered.subList(start, end).stream()
                .map(TaskResponse::new)
                .toList();

        return new TaskPageResponse(
                paged,
                (int) Math.ceil((double) filtered.size() / (size != null ? size : filtered.size())),
                filtered.size(),
                size != null ? size : filtered.size(),
                page != null ? page : 0
        );
    }


    /**
     * 일정 단건 조회
     *
     * @param taskId 조회할 일정 ID
     * @return 일정 상세 정보
     */
    public TaskDetailResponse getTaskById(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        return new TaskDetailResponse(task);
    }

    /**
     * 일정을 수정합니다. 생성자 또는 담당자만 수정 권한이 있으며,
     * 상태는 유효한 순서 (TODO → IN_PROGRESS → DONE)로만 변경 가능합니다.
     *
     * @param taskId   수정할 일정 ID
     * @param request  일정 수정 요청 DTO
     * @param memberId 로그인한 사용자 ID
     * @return 수정된 일정 상세 정보 DTO
     */
    public TaskDetailResponse updateTask(Long taskId, TaskUpdateRequest request, Long memberId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        Member assignee = memberRepository.findById(request.getAssigneeId())
                .orElseThrow(AssigneeNotFoundException::new);

        // 권한 확인: 요청자가 생성자 현재 할당된 담당자인지 확인
        if (!Objects.equals(task.getAssignee().getId(), memberId)
                && !Objects.equals(task.getCreator().getId(), memberId)) {
            throw new UnauthorizedStatusChangeException();
        }



        // 상태 순서 검증
        TaskStatus currentStatus = task.getStatus();
        TaskStatus newStatus = request.getStatus();

        boolean validTransition =
                (currentStatus == TaskStatus.TODO && newStatus == TaskStatus.IN_PROGRESS) ||
                        (currentStatus == TaskStatus.IN_PROGRESS && newStatus == TaskStatus.DONE) ||
                        (currentStatus == newStatus); // 같은 상태로는 허용

        if (!validTransition) {
            throw new InvalidStatusTransitionException(); // 커스텀 예외
        }

        // IN_PROGRESS 상태로 바뀔 때 시작일 기록
        if (currentStatus != TaskStatus.IN_PROGRESS && newStatus == TaskStatus.IN_PROGRESS && task.getStartDate() == null) {
            task.setStartDate(LocalDateTime.now());
        }

        task.update(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                newStatus,
                request.getDueDate(),
                assignee
        );

        return new TaskDetailResponse(task);
    }

    /**
     * 일정 삭제
     *
     * @param taskId 삭제할 일정 ID
     */
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.delete();
    }

    /**
     * 일정 상태만 변경
     *
     * @param taskId 대상 일정 ID
     * @param status 변경할 상태
     * @return 상태가 변경된 일정 상세 정보
     */
    public TaskDetailResponse updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (status == null) {
            throw new InvalidStatusException();
        }
        task.changeStatus(status);
        return new TaskDetailResponse(task);
    }
}
