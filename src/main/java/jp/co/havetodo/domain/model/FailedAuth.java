package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "failed_auth")
public record FailedAuth(@Column(value = "account_id") @NonNull @NotNull Long accountId,
                         @Column(value = "auth_ts") @NonNull @NotNull ZonedDateTime authTs) implements
    Serializable {

}
