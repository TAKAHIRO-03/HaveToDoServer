package jp.co.havetodo.domain.model;

import java.io.Serializable;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "executed_task_status")
public record ExecutedTaskStatus(
    @Id @Column(value = "name") @NonNull @NotNull String name) implements
    Serializable {

}
