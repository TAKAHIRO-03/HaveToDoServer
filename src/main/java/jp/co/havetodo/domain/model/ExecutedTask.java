package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "executed_task")
public record ExecutedTask(
    @Id @Column(value = "planned_task_id") @NonNull @NotNull Long plannedTaskId,
    @Column(value = "started_time") @NonNull @NotNull ZonedDateTime startedTime,
    @Column(value = "ended_time") @NonNull @NotNull ZonedDateTime endedTime,
    @Transient @NonNull @NotNull ExecutedTaskStatus executedTaskStatus) implements
    Serializable {

}
