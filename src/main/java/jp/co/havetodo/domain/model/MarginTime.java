package jp.co.havetodo.domain.model;

import io.r2dbc.postgresql.codec.Interval;
import java.io.Serializable;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "margin_time")
public record MarginTime(@Id @Column(value = "id") @NonNull @NotNull Integer id,
                         @Column(value = "completed_margin") @NonNull @NotNull Interval completedMargin,
                         @Column(value = "cancel_margin") @NonNull @NotNull Interval cancelMargin) implements
    Serializable {

}
