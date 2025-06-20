package com.taskflow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TimeComparisonTest {

    @Test
    void 기한_초과_비교_테스트() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDatePast = now.minusDays(1);
        LocalDateTime dueDateFuture = now.plusDays(1);

        assertTrue(dueDatePast.isBefore(now));
        assertFalse(dueDateFuture.isBefore(now));
    }
}
