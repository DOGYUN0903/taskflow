package com.taskflow.domain.task.service;

import com.taskflow.domain.member.entity.Member;
import com.taskflow.domain.member.repository.MemberRepository;
import com.taskflow.domain.task.dto.TaskCreateRequest;
import com.taskflow.domain.task.dto.TaskResponse;
import com.taskflow.domain.task.dto.TaskUpdateRequest;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskPriority;
import com.taskflow.domain.task.enums.TaskStatus;
import com.taskflow.domain.task.exception.*;
import com.taskflow.domain.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    /**
     * 일정 생성
     */
    @Transactional
    public void createTask(TaskCreateRequest request, String creatorEmail) {
        // 회원이 탈퇴했는데 토큰은 유효해서 요청을 보낼 경우 예외
        Member creator = memberRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new IllegalArgumentException("생성자를 찾을 수 없습니다."));

        // 담당자가 지정이 안되었을 경우 예외
        Member manager = memberRepository.findByEmail(request.getManagerName())
                .orElseThrow(ManagerNotFoundException::new);

        // 마감일이 현재보다 이전일 경우 예외
        if (request.getDueDate() != null && request.getDueDate().isBefore(LocalDate.now().atStartOfDay())) {
            throw new PastDueDateException();
        }

        Task task = Task.builder()
                .creator(creator)
                .manager(manager)
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM)
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .dueDate(request.getDueDate())
                .startDate(request.getStartDate())
                .isDeleted(false)
                .build();

        taskRepository.save(task);
    }

    /**
     * 상태별 일정 목록 그룹핑 (칸반보드 용)
     */
    public Map<TaskStatus, List<TaskResponse>> getTasksByStatusGrouped() {
        List<Task> allTasks = taskRepository.findAllByIsDeletedFalse();

        return allTasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getStatus,
                        () -> new EnumMap<>(TaskStatus.class),
                        Collectors.mapping(TaskResponse::forKanban, Collectors.toList())
                ));
    }


    /**
     * 일정 단건 조회
     */
    public TaskResponse getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.getIsDeleted())
                .orElseThrow(TaskNotFoundException::new);
        return TaskResponse.from(task);
    }

    /**
     * 일정 수정 - 생성자 또는 담당자만 수정 가능
     */
    @Transactional
    public void updateTask(Long taskId, TaskUpdateRequest request, String requesterEmail) {
        // 수정할 일정이 없을 경우 예외
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        // 회원이 탈퇴했는데 토큰은 유효해서 요청을 보낼 경우 예외
        Member requester = memberRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new IllegalArgumentException("수정 요청자를 찾을 수 없습니다."));

        // 생성자, 담당자가 아닐 경우 예외
        if (!task.getCreator().equals(requester) && !task.getManager().equals(requester)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 담당자가 없을 경우 예외
        Member manager = memberRepository.findByEmail(request.getManagerName())
                .orElseThrow(ManagerNotFoundException::new);

        // 마감일이 현재보다 이전일 경우 예외
        if (request.getDueDate() != null && request.getDueDate().isBefore(LocalDate.now().atStartOfDay())) {
            throw new PastDueDateException();
        }

        task.update(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus() != null ? request.getStatus() : TaskStatus.TODO,
                request.getDueDate(),
                request.getStartDate(),
                manager
        );


    }

    /**
     * 일정 삭제 (Soft Delete)
     */
    @Transactional
    public void deleteTask(Long taskId) {
        // 삭제할 일정이 없을 경우 예외
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task.softDelete();
    }

    /**
     * 일정 키워드 검색 (제목 또는 설명)
     */
    /*
    public List<TaskResponse> searchTasksByKeyword(String keyword) {
        List<Task> allTasks = taskRepository.findAllByIsDeletedFalse();

        return allTasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        (task.getDescription() != null && task.getDescription().toLowerCase().contains(keyword.toLowerCase())))
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }
    */
}
