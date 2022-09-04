package jp.co.havetodo.service.model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import jp.co.havetodo.domain.model.Account;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public record CreateTaskInputData(
    @NonNull @NotNull Account account,
    @NonNull @NotNull String title,
    @NonNull @NotNull String description,
    @NonNull @NotNull LocalDateTime startTime,
    @NonNull @NotNull LocalDateTime endTime,
    @NonNull @NotNull BigDecimal cost,
    boolean isRepeat,
    Set<DayOfWeek> repeatDayOfWeek,
    LocalDate repeatEndDate) {

}
