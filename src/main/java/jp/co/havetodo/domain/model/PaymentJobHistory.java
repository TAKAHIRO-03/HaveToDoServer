package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "payment_job_history")
public record PaymentJobHistory(
    @Id @Column(value = "task_id") @NonNull @NotNull Long taskId,
    @Column(value = "executed_time") @NonNull @NotNull LocalDateTime executedTime)
    implements Serializable {

}
