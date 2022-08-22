package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "executed_task")
public record ExecutedTask(@Id @NonNull @NotNull PlannedTask plannedTask,
                           @Column(value = "started_time") @NonNull @NotNull ZonedDateTime startedTime,
                           @Column(value = "ended_time") @NonNull @NotNull ZonedDateTime endedTime,
                           @NonNull @NotNull ExecutedTaskStatus executedTaskStatus) implements
    Serializable {

}
