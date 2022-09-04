package jp.co.havetodo.api.payload.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringJUnitConfig(TaskRequestTest.Config.class)
public class TaskRequestTest {

    @Autowired
    private Validator validator;

    @Configuration
    static class Config {

        @Bean
        LocalValidatorFactoryBean localValidatorFactoryBean() {
            return new LocalValidatorFactoryBean();
        }

    }

    @ParameterizedTest
    @MethodSource
    public void Task作成リクエスト_正常系(final String title, final String description,
        final LocalDateTime startTime,
        final LocalDateTime endTime, final BigDecimal cost, final boolean isRepeat,
        final Set<DayOfWeek> dayOfWeeks,
        final LocalDate endDate) {

        final var req = new TaskRequest(title, description, startTime, endTime, cost, isRepeat,
            dayOfWeeks, endDate);
        final var result = new BindException(req, "taskRequest");
        this.validator.validate(req, result);

        result.getFieldErrors().forEach(System.out::println);

        assertFalse(result.hasFieldErrors());
    }

    static Stream<Arguments> Task作成リクエスト_正常系() {
        return Stream.of(
            arguments("t", "d", LocalDateTime.now().plus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().plus(1, ChronoUnit.HOURS),
                BigDecimal.valueOf(1), false, Collections.emptySet(), null),
            arguments(
                "titletitletitletitletitletletitletitletitletitletitletitletitletitletitletitletitletitletitletitleti",
                "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptio",
                LocalDateTime.now().plus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().plus(1, ChronoUnit.HOURS),
                BigDecimal.valueOf(10000), false, Collections.emptySet(), null),
            arguments("t", "d", LocalDateTime.now().plus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().plus(1, ChronoUnit.HOURS),
                BigDecimal.valueOf(1), true, Set.of(DayOfWeek.MONDAY),
                LocalDate.now().plus(1, ChronoUnit.DAYS)),
            arguments("t", "d", LocalDateTime.now().plus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().plus(1, ChronoUnit.HOURS),
                BigDecimal.valueOf(1), true,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                LocalDate.now().plus(365, ChronoUnit.DAYS)));
    }

    @ParameterizedTest
    @MethodSource
    public void Task作成リクエスト_異常系(final String title, final String description,
        final LocalDateTime startTime,
        final LocalDateTime endTime, final BigDecimal cost, final boolean isRepeat,
        final Set<DayOfWeek> dayOfWeeks,
        final LocalDate endDate) {

        final var req = new TaskRequest(title, description, startTime, endTime, cost, isRepeat,
            dayOfWeeks, endDate);
        final var result = new BindException(req, "taskRequest");
        this.validator.validate(req, result);

        result.getFieldErrors().forEach(System.out::println);

        assertTrue(6 <= result.getErrorCount());
    }

    static Stream<Arguments> Task作成リクエスト_異常系() {
        return Stream.of(
            arguments("", "", LocalDateTime.now().minus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().minus(1, ChronoUnit.HOURS),
                BigDecimal.valueOf(0), true, Set.of(DayOfWeek.MONDAY), null),
            arguments(
                "titletitletitletitletitletletitletitletitletitletitletitletitletitletitletitletitletitletitletitletit",
                "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription",
                LocalDateTime.now().minus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().minus(1, ChronoUnit.HOURS),
                BigDecimal.valueOf(10001), true, Collections.emptySet(),
                LocalDate.now().plus(365, ChronoUnit.DAYS))
        );
    }


}
