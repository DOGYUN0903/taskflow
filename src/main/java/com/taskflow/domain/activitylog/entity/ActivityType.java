package com.taskflow.domain.activitylog.entity;

public enum ActivityType {
    // task 활동유형
    TASK_CREATED,
    TASK_UPDATED,
    TASK_DELETED,
    TASK_STATUS_CHANGED,

    // comment 활동유형
    COMMENT_CREATED,
    COMMENT_UPDATED,
    COMMENT_DELETED,

    // user 활동유형
    USER_LOGGED_IN,
    USER_LOGGED_OUT,
    USER_WITHDRAW
}
