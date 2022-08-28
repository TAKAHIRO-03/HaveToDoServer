package jp.co.havetodo.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

@Table(value = "task")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public final class Task implements Serializable {

    @Id
    @Column(value = "id")
    @Nullable
    private final Long id;

    @Column(value = "account_id")
    @NonNull
    @NotNull
    private final Long accountId;

    @Column(value = "title")
    @NonNull
    @NotNull
    private final String title;

    @Column(value = "description")
    @NonNull
    @NotNull
    private final String description;

    @Column(value = "start_time")
    @NonNull
    @NotNull
    private final LocalDateTime startTime;

    @Column(value = "end_time")
    @NonNull
    @NotNull
    private final LocalDateTime endTime;

    @Column(value = "cost")
    @NonNull
    @NotNull
    private final BigDecimal cost;

    @Column(value = "is_repeat")
    @NonNull
    @NotNull
    private final Boolean isRepeat;

    @Transient
    @NonNull
    @NotNull
    @Setter
    private Optional<ExecutedTask> executedTask;

    @Transient
    @NonNull
    @NotNull
    @Setter
    private Optional<PaymentJobHistory> paymentJobHistory;

    public Flux<Task> createTasks(@NonNull @NotNull final Set<DayOfWeek> dayOfWeeks,
        @NonNull @NotNull final LocalDate repeatEndDate) {

        if (CollectionUtils.isEmpty(dayOfWeeks)) {
            throw new IllegalArgumentException("dayOfWeeks must not be empty.");
        }

        final var endTimeDateToRepeatEndDateDuration = Duration.between(this.endTime,
            LocalDateTime.of(repeatEndDate, LocalTime.of(0, 0)));
        final var weeks = (int) endTimeDateToRepeatEndDateDuration.toDaysPart() / 7;

        final var tasks = new ArrayList<Flux<Task>>();
        tasks.add(Flux.just(this)); // add me

        for (final var dayOfWeek : dayOfWeeks) {
            tasks.add(Flux.range(1, weeks).map(week -> Task.builder()
                .accountId(this.accountId)
                .title(this.title)
                .description(this.description)
                .startTime(this.startTime.plusWeeks(week).with(dayOfWeek))
                .endTime(this.endTime.plusWeeks(week).with(dayOfWeek))
                .isRepeat(this.isRepeat)
                .cost(this.cost)
                .build()
            ));
        }

        return Flux.merge(tasks).distinct(Task::getStartTime).distinct(Task::getEndTime);
    }


    /* builder exclude executedTask and paymentJobHistory */
    Task(@Nullable final Long id, @NonNull @NotNull final Long accountId,
        @NonNull @NotNull final String title,
        @NonNull @NotNull final String description, @NonNull @NotNull final LocalDateTime startTime,
        @NonNull @NotNull final LocalDateTime endTime, @NonNull @NotNull final BigDecimal cost,
        @NonNull @NotNull final Boolean isRepeat) {
        this.id = id;
        this.accountId = accountId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.isRepeat = isRepeat;
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public static class TaskBuilder {

        private @Nullable Long id;
        private @NonNull @NotNull Long accountId;
        private @NonNull @NotNull String title;
        private @NonNull @NotNull String description;
        private @NonNull @NotNull LocalDateTime startTime;
        private @NonNull @NotNull LocalDateTime endTime;
        private @NonNull @NotNull BigDecimal cost;
        private @NonNull @NotNull Boolean isRepeat;

        TaskBuilder() {
        }

        public TaskBuilder id(@Nullable final Long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder accountId(@NonNull @NotNull final Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public TaskBuilder title(@NonNull @NotNull final String title) {
            this.title = title;
            return this;
        }

        public TaskBuilder description(@NonNull @NotNull final String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder startTime(@NonNull @NotNull final LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public TaskBuilder endTime(@NonNull @NotNull final LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public TaskBuilder cost(@NonNull @NotNull final BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public TaskBuilder isRepeat(@NonNull @NotNull final Boolean isRepeat) {
            this.isRepeat = isRepeat;
            return this;
        }

        public Task build() {
            return new Task(this.id, this.accountId, this.title, this.description, this.startTime,
                this.endTime, this.cost, this.isRepeat);
        }

    }

}
