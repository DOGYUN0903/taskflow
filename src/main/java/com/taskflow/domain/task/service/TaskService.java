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
 * TaskService는 일정(Task) 도메인의 핵심 비즈니스 로직을 담당하는 서비스 클래스
 * 일정 생성, 조회, 수정, 삭제, 상태 변경 기능 제공
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    /**
     * 일정을 생성하는 서비스 로직
     *
     * @param request  일정 생성 요청 DTO
     * @param memberId 생성자 회원 ID
     * @return 생성된 일정 상세 응답 DTO
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
     * 일정 목록을 조회하는 서비스 로직
     * 상태, 키워드, 담당자 ID 필터링 및 페이징 처리 지원
     *
     * @param status     일정 상태
     * @param page       페이지 번호
     * @param size       페이지 크기
     * @param search     검색 키워드
     * @param assigneeId 담당자 ID
     * @return 페이징 처리된 일정 목록 응답 DTO
     */
    @Transactional(readOnly = true)
    public TaskPageResponse getTasks(TaskStatus status, Integer page, Integer size, String search, Long assigneeId) {
        List<Task> tasks = taskRepository.findAllByIsDeletedFalse();

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

        if (filtered.isEmpty()) {
            if (search != null) {
                throw new TaskNotFoundException(TaskError.TASK_NOT_FOUND_BY_SEARCH);
            }
            if (assigneeId != null) {
                throw new TaskNotFoundException(TaskError.TASK_NOT_FOUND_BY_ASSIGNEE);
            }

            return new TaskPageResponse(
                    Collections.emptyList(),
                    0, 0,
                    size != null ? size : 0,
                    page != null ? page : 0
            );
        }

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
     * 일정 단건을 조회하는 서비스 로직
     *
     * @param taskId 조회할 일정 ID
     * @return 일정 상세 응답 DTO
     */
    public TaskDetailResponse getTaskById(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        return new TaskDetailResponse(task);
    }

    /**
     * 일정을 수정하는 서비스 로직
     * 생성자 또는 담당자만 수정 가능하며, 상태 순서 유효성 검증 포함
     *
     * @param taskId   수정할 일정 ID
     * @param request  수정 요청 DTO
     * @param memberId 요청자 회원 ID
     * @return 수정된 일정 상세 응답 DTO
     */
    public TaskDetailResponse updateTask(Long taskId, TaskUpdateRequest request, Long memberId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        Member assignee = memberRepository.findById(request.getAssigneeId())
                .orElseThrow(AssigneeNotFoundException::new);

        if (!Objects.equals(task.getAssignee().getId(), memberId)
                && !Objects.equals(task.getCreator().getId(), memberId)) {
            throw new UnauthorizedStatusChangeException();
        }

        TaskStatus currentStatus = task.getStatus();
        TaskStatus newStatus = request.getStatus() != null ? request.getStatus() : currentStatus;

        if (request.getStatus() != null) {
            boolean validTransition =
                    (currentStatus == TaskStatus.TODO && newStatus == TaskStatus.IN_PROGRESS) ||
                            (currentStatus == TaskStatus.IN_PROGRESS && newStatus == TaskStatus.DONE) ||
                            (currentStatus == newStatus);

            if (!validTransition) {
                throw new InvalidStatusTransitionException();
            }

            if (currentStatus != TaskStatus.IN_PROGRESS
                    && newStatus == TaskStatus.IN_PROGRESS
                    && task.getStartDate() == null) {
                task.setStartDate(LocalDateTime.now());
            }
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
     * 일정을 삭제하는 서비스 로직 (Soft Delete 방식)
     *
     * @param taskId 삭제 대상 일정 ID
     */
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.delete();
    }

    /**
     * 일정 상태만 별도로 변경하는 서비스 로직
     * 생성자 또는 담당자만 변경 가능하며 상태 전이 순서 검증 포함
     *
     * @param taskId    일정 ID
     * @param newStatus 변경할 상태
     * @param memberId  요청자 회원 ID
     * @return 상태가 변경된 일정 상세 응답 DTO
     */
    public TaskDetailResponse updateTaskStatus(Long taskId, TaskStatus newStatus, Long memberId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        if (!Objects.equals(task.getAssignee().getId(), memberId)
                && !Objects.equals(task.getCreator().getId(), memberId)) {
            throw new UnauthorizedStatusChangeException();
        }

        TaskStatus currentStatus = task.getStatus();

        boolean validTransition =
                (currentStatus == TaskStatus.TODO && newStatus == TaskStatus.IN_PROGRESS) ||
                        (currentStatus == TaskStatus.IN_PROGRESS && newStatus == TaskStatus.DONE) ||
                        (currentStatus == newStatus);

        if (!validTransition) {
            throw new InvalidStatusTransitionException();
        }

        if (currentStatus != TaskStatus.IN_PROGRESS
                && newStatus == TaskStatus.IN_PROGRESS
                && task.getStartDate() == null) {
            task.setStartDate(LocalDateTime.now());
        }

        task.changeStatus(newStatus);
        return new TaskDetailResponse(task);
    }
}
