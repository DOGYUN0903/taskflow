package com.taskflow.domain.dashboard.dto;

import com.taskflow.domain.task.entity.Task;

import java.time.LocalDateTime;

public record TaskSimpleResponse(Long id, String title, String priority, LocalDateTime dueDate) {

    public static TaskSimpleResponse from(Task task) {
        return new TaskSimpleResponse(
                task.getId(),
                task.getTitle(),
                task.getPriority().name(),
                task.getDueDate()
        );
    }

}
