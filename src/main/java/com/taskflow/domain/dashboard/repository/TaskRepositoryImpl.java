package com.taskflow.domain.dashboard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taskflow.domain.dashboard.dto.TaskStatisticsResponse;

import com.taskflow.domain.task.entity.QTask;
import com.taskflow.domain.task.entity.Task;
import com.taskflow.domain.task.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TaskRepositoryImpl implements TaskRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public TaskStatisticsResponse fetchTaskStatistics() {
        QTask task = QTask.task;
        LocalDateTime now = LocalDateTime.now();

        long total = queryFactory.select(task.count())
                .from(task)
                .where(task.isDeleted.isFalse())
                .fetchOne();

        long todo = queryFactory.select(task.count())
                .from(task)
                .where(task.isDeleted.isFalse()
                        .and(task.status.eq(TaskStatus.valueOf("TODO"))))
                .fetchOne();

        long inProgress = queryFactory.select(task.count())
                .from(task)
                .where(task.isDeleted.isFalse()
                        .and(task.status.eq(TaskStatus.valueOf("IN_PROGRESS"))))
                .fetchOne();

        long done = queryFactory.select(task.count())
                .from(task)
                .where(task.isDeleted.isFalse()
                        .and(task.status.eq(TaskStatus.valueOf("DONE"))))
                .fetchOne();

        long overdue = queryFactory.select(task.count())
                .from(task)
                .where(task.isDeleted.isFalse()
                        .and(task.status.in(TaskStatus.TODO, TaskStatus.IN_PROGRESS))
                        .and(task.dueDate.before(now)))
                .fetchOne();

        double completionRate = total == 0 ? 0.0 : Math.round((done * 10000.0 / total)) / 100.0;

        return new TaskStatisticsResponse(total, todo, inProgress, done, completionRate, overdue);
    }

    @Override
    public List<Task> findTodayTasksByStatusAndAssignee(TaskStatus status, Long assigneeId) {
        QTask task = QTask.task;

        return queryFactory
                .selectFrom(task)
                .where(
                        task.assignee.id.eq(assigneeId),
                        task.status.eq(status),
                        task.isDeleted.isFalse(),
                        task.createdAt.between(LocalDate.now().atStartOfDay(), LocalDateTime.now())
                )
                .orderBy(task.priority.desc())  // HIGH > MEDIUM > LOW
                .fetch();
    }
}

