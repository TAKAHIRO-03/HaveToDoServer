package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.Column;


public record FailedAuthId(@Column(value = "account_id") @NonNull @NotNull Long accountId,
                           @Column(value = "auth_ts") @NonNull @NotNull LocalDateTime authTs) implements
    Serializable {

}
