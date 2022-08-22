package jp.co.havetodo.domain.model;

import io.r2dbc.postgresql.codec.Interval;
import java.io.Serializable;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "timezones")
public record Timezones(@Id @Column(value = "name") @NonNull @NotNull String name,
                        @Column(value = "abbrev") @NonNull @NotNull String abbrev,
                        @Column(value = "utc_offset") @NonNull @NotNull Interval utcOffset,
                        @Column(value = "is_dst") @NonNull @NotNull Boolean isDst) implements
    Serializable {

}
