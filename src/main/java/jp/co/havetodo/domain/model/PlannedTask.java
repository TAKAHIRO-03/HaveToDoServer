package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Accessors(fluent = true)
@Builder
@Table(value = "planned_task")
public class PlannedTask implements Serializable {

    @Id
    @Column(value = "id")
    @NonNull
    @NotNull
    Long id;

    @NonNull
    @NotNull
    Account account;

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
    Boolean isRepeat;

    @NonNull
    @NotNull
    ExecutedTask executedTask;

    @NonNull
    @NotNull
    PaymentJobHistory paymentJobHistory;

}
