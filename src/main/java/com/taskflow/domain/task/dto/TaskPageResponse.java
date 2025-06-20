package com.taskflow.domain.task.dto;

import lombok.Getter;

import java.util.List;

/**
 * 일정 목록 조회 시 페이징 응답을 위한 DTO
 * 일정 목록(List)과 페이징 정보(totalPages, totalElements 등)를 포함
 */
@Getter
public class TaskPageResponse {

    /**
     * 현재 페이지에 포함된 일정 목록
     */
    private final List<TaskResponse> content;

    /**
     * 전체 페이지 수
     */
    private final int totalPages;

    /**
     * 전체 일정 개수
     */
    private final long totalElements;

    /**
     * 페이지당 항목 수
     */
    private final int size;

    /**
     * 현재 페이지 번호 (0부터 시작)
     */
    private final int number;

    /**
     * 전체 페이징 응답을 생성하는 생성자
     *
     * @param content 일정 목록
     * @param totalPages 전체 페이지 수
     * @param totalElements 전체 일정 개수
     * @param size 페이지당 크기
     * @param number 현재 페이지 번호
     */
    public TaskPageResponse(List<TaskResponse> content, int totalPages, long totalElements, int size, int number) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.number = number;
    }
}
