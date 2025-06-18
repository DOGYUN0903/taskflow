package com.taskflow.domain.activitylog.service;

import com.taskflow.domain.activitylog.entity.ActivityType;

public interface ActivityService {
    void saveLog(Long userId, String ip, String method, String url, ActivityType type, Long targetId);
}
