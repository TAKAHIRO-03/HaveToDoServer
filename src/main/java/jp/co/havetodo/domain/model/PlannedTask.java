package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Builder
@Table(value = "planned_task")
public class PlannedTask implements Serializable {

    @Id
    @Column(value = "id")
    @NonNull
    @NotNull
    Long id;

    @Column(value = "account_id")
    @NonNull
    @NotNull
    Long accountId;

    @Column(value = "title")
    @NonNull
    @NotNull
    String title;

    @Column(value = "description")
    @NonNull
    @NotNull
    String description;

    @Column(value = "start_time")
    @NonNull
    @NotNull
    ZonedDateTime startTime;

    @Column(value = "end_time")
    @NonNull
    @NotNull
    ZonedDateTime endTime;

    @Column(value = "cost")
    @NonNull
    @NotNull
    BigDecimal cost;

    @Column(value = "is_repeat")
    @NonNull
    @NotNull
    Boolean isRepeat;

    @Transient
    @NonNull
    @NotNull
    Optional<ExecutedTask> executedTask;

    @Transient
    @NonNull
    @NotNull
    Optional<PaymentJobHistory> paymentJobHistory;

}
