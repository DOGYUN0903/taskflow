package com.taskflow.domain.task.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TaskPageResponse {

    private final List<TaskResponse> content;
    private final int totalPages;
    private final long totalElements;
    private final int size;
    private final int number;

    public TaskPageResponse(List<TaskResponse> content, int totalPages, long totalElements, int size, int number) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.number = number;
    }
}
