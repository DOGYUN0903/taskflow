package com.taskflow.domain.task.service;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.task.dto.*;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.exception.CreatorNotFoundException;
import com.taskflow.domain.task.exception.InvalidStatusException;
import com.taskflow.domain.task.exception.AssigneeNotFoundException;
import com.taskflow.domain.task.exception.TaskNotFoundException;
import com.taskflow.domain.task.repository.TaskRepository;
import com.taskflow.global.response.error.TaskError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    public TaskDetailResponse createTask(TaskCreateRequest request, String creatorEmail) {
        Member creator = memberRepository.findByEmail(creatorEmail)
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
     * 일정 전체 조회: 검색, 상태, 담당자 필터 + 페이징 처리
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
                .filter(task -> assigneeId == null || Objects.equals(task.getManager().getId(), assigneeId))
                .collect(Collectors.toList());

        // 예외 처리
        if (filtered.isEmpty()) {
            if (search != null) throw new TaskNotFoundException(TaskError.TASK_NOT_FOUND_BY_SEARCH);
            if (assigneeId != null) throw new TaskNotFoundException(TaskError.TASK_NOT_FOUND_BY_ASSIGNEE);
            throw new TaskNotFoundException(); // 기본 메시지
        }

        // 페이징 처리
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



    public TaskDetailResponse getTaskById(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        return new TaskDetailResponse(task);
    }

    public TaskDetailResponse updateTask(Long taskId, TaskUpdateRequest request, String requesterEmail) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        Member assignee = memberRepository.findById(request.getAssigneeId())
                .orElseThrow(AssigneeNotFoundException::new);

        task.update(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus(),
                request.getDueDate(),
                assignee
        );

        return new TaskDetailResponse(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.delete();
    }

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
