package jp.co.havetodo.service.model;

import java.time.LocalDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;

public record FindTasksInputData(
    @NonNull @NotNull Long accountId,
    @NonNull @NotNull Pageable page,
    @Nullable LocalDateTime startTime,
    @NonNull @NotNull LocalDateTime endTime
) {

}
