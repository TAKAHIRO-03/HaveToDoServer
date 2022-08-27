package jp.co.havetodo.service.model;

import java.time.ZonedDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;

public record FindPlannedTasksInputData(
    @NonNull @NotNull Long accountId,
    @NonNull @NotNull Pageable page,
    @Nullable ZonedDateTime startTime,
    @NonNull @NotNull ZonedDateTime endTime
) {

}
