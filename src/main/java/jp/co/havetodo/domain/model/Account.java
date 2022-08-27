package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Builder
@Table(value = "account")
public class Account implements Serializable {

    @Id
    @Column(value = "id")
    @NonNull
    @NotNull
    Long id;

    @Column(value = "username")
    @NonNull
    @NotNull
    String username;

    @Column(value = "password")
    @NonNull
    @NotNull
    String password;

    @Column(value = "is_locked")
    Boolean isLocked;

    @NonNull
    @NotNull
    Timezones timezones;

    @NonNull
    @NotNull
    Currency currency;

    @Nullable
    OauthProvider oauthProvider;

    @NonNull
    @NotNull
    @Column(value = "created_time")
    LocalDateTime createdTime;

    @NonNull
    @NotNull
    @Column(value = "updated_time")
    LocalDateTime updatedTime;

    @NonNull
    @NotNull
    List<Task> tasks;

    @NonNull
    @NotNull
    List<FailedAuth> failedAuths;

}
