package com.taskflow.domain.activitylog.service;

import com.taskflow.domain.activitylog.entity.ActivityLog;
import com.taskflow.domain.activitylog.entity.ActivityType;
import com.taskflow.domain.activitylog.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityLogRepository activityLogRepository;

    @Override
    public void saveLog(Long userId, String ip, String method, String url, ActivityType type, Long targetId) {
        ActivityLog activityLog = ActivityLog.builder()
                .userId(userId)
                .ipAddress(ip)
                .httpMethod(method)
                .url(url)
                .activityType(type)
                .targetId(targetId)
                .build();

        activityLogRepository.save(activityLog);
    }
}
