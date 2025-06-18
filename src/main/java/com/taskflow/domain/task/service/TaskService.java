package com.taskflow.domain.task.service;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.task.dto.TaskCreateRequest;
import com.taskflow.domain.task.dto.TaskDetailResponse;
import com.taskflow.domain.task.dto.TaskResponse;
import com.taskflow.domain.task.dto.TaskUpdateRequest;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.exception.CreatorNotFoundException;
import com.taskflow.domain.task.exception.ManagerNotFoundException;
import com.taskflow.domain.task.exception.TaskNotFoundException;
import com.taskflow.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    /**
     * 일정 생성
     */
    public TaskDetailResponse createTask(TaskCreateRequest request, String creatorEmail) {
        Member creator = memberRepository.findByEmail(creatorEmail)
                .orElseThrow(CreatorNotFoundException::new);

        Member assignee = memberRepository.findById(request.getAssigneeId())
                .orElseThrow(ManagerNotFoundException::new);

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
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(TaskStatus status, Integer page, Integer size, String search, Long assigneeId) {
        List<Task> tasks = taskRepository.findAllByIsDeletedFalse();

        return tasks.stream()
                .filter(task -> status == null || task.getStatus() == status)
                .filter(task -> search == null || task.getTitle().contains(search) || task.getDescription().contains(search))
                .filter(task -> assigneeId == null || task.getManager().getId().equals(assigneeId))
                .skip(page != null && size != null ? (long) page * size : 0)
                .limit(size != null ? size : tasks.size())
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 일정 단건 조회
     */
    @Transactional(readOnly = true)
    public TaskDetailResponse getTaskById(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        return new TaskDetailResponse(task);
    }

    /**
     * 일정 수정
     */
    public TaskDetailResponse updateTask(Long taskId, TaskUpdateRequest request, String requesterEmail) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        Member assignee = memberRepository.findById(request.getAssigneeId())
                .orElseThrow(ManagerNotFoundException::new);

        task.update(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus(),
                request.getStartDate(),
                request.getDueDate(),
                assignee
        );

        return new TaskDetailResponse(task);
    }

    /**
     * 일정 삭제 (소프트 딜리트)
     */
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        task.delete(); // 소프트 딜리트 처리
    }

    /**
     * 일정 상태 변경
     */
    public TaskDetailResponse updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(TaskNotFoundException::new);

        task.changeStatus(status);
        return new TaskDetailResponse(task);
    }
}
