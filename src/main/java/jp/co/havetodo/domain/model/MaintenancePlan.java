package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "maintenance_plan")
public record MaintenancePlan(@Id @Column(value = "id") @NonNull @NotNull Integer id,
                              @Column(value = "start_time") @NonNull @NotNull ZonedDateTime startTime,
                              @Column(value = "end_time") @NonNull @NotNull ZonedDateTime endTime) implements
    Serializable {

}
