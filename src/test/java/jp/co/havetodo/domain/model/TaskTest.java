package jp.co.havetodo.domain.model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class TaskTest {

    @Test
    public void 複数タスク生成テスト() throws Exception {
        final var task = Task.builder()
            .accountId(1L)
            .title("title-%d".formatted(1))
            .description("description-%d".formatted(1))
            .startTime(LocalDateTime.of(2022, 1, 1, 10, 10, 10))
            .endTime(LocalDateTime.of(2022, 1, 1, 13, 10, 10))
            .isRepeat(false)
            .cost(BigDecimal.valueOf(1000))
            .build();

        System.out.println("same day of week");
        task.createTasks(Set.of(DayOfWeek.SATURDAY), LocalDate.of(2023, 1, 1))
            .log()
            .count()
            .as(StepVerifier::create)
            .expectNextMatches(tasks -> tasks.equals(53L))
            .verifyComplete();

        System.out.println();

        System.out.println("difference day of week");
        task.createTasks(Set.of(DayOfWeek.MONDAY), LocalDate.of(2023, 1, 1))
            .log()
            .count()
            .as(StepVerifier::create)
            .expectNextMatches(tasks -> tasks.equals(53L))
            .verifyComplete();

        System.out.println();

        System.out.println("full day of week");
        task.createTasks(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                LocalDate.of(2023, 1, 1))
            .log()
            .count()
            .as(StepVerifier::create)
            .expectNextMatches(tasks -> tasks.equals(365L))
            .verifyComplete();

    }

}