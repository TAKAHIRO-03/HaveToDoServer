package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "executed_task_status")
public record ExecutedTaskStatus(
    @Id @Column(value = "name") @NonNull @NotNull String name) implements
    Serializable {

    private static Set<String> names = new HashSet<>(
        Arrays.asList("NOT_ACHIEVED", "ACHIEVED", "CANCELED"));

    public ExecutedTaskStatus(final String name) {
        if (!names.contains(name)) { //
            throw new IllegalArgumentException(
                "name must be " + names + " but was \"" + name + "\".");
        }
        this.name = name;
    }

    public static void setName(final Set<String> _names) {
        names.clear();
        names.addAll(_names);
        names = Collections.unmodifiableSet(names);
    }

}
