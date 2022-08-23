package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "payment_job_history")
public record PaymentJobHistory(
    @Id @Column(value = "planned_task_id") @NonNull @NotNull Long plannedTaskId,
    @Column(value = "executed_time") @NonNull @NotNull ZonedDateTime executedTime)
    implements Serializable {

}
