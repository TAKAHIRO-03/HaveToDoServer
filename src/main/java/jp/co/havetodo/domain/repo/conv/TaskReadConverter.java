package jp.co.havetodo.domain.repo.conv;


import static java.util.Objects.nonNull;

import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import jp.co.havetodo.domain.model.ExecutedTask;
import jp.co.havetodo.domain.model.ExecutedTaskStatus;
import jp.co.havetodo.domain.model.PaymentJobHistory;
import jp.co.havetodo.domain.model.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TaskReadConverter implements Converter<Row, Task> {

    @Override
    public Task convert(final Row r) {

        // fetch executed_task table
        final var existsExecutedTaskTaskIdColumnAndNotNull = r.getMetadata()
            .contains("executed_task_task_id") && nonNull(
            r.get("executed_task_task_id", Long.class));
        final Optional<ExecutedTask> executedTask =
            existsExecutedTaskTaskIdColumnAndNotNull ? Optional.of(
                new ExecutedTask(r.get("executed_task_task_id", Long.class),
                    r.get("started_time", LocalDateTime.class),
                    r.get("ended_time", LocalDateTime.class),
                    new ExecutedTaskStatus(r.get("executed_task_status_name", String.class))))
                : Optional.empty();

        // fetch payment_job_history table
        final var existsPaymentJobHistoryTaskIdColumnAndNotNull = r.getMetadata()
            .contains("payment_job_history_task_id") && nonNull(
            r.get("payment_job_history_task_id", Long.class));
        final Optional<PaymentJobHistory> paymentJobHistory =
            existsPaymentJobHistoryTaskIdColumnAndNotNull ? Optional.of(
                new PaymentJobHistory(r.get("payment_job_history_task_id", Long.class),
                    r.get("executed_time", LocalDateTime.class))) : Optional.empty();

        final var task = Task.builder()
            .id(r.get("id", Long.class))
            .accountId(r.get("account_id", Long.class))
            .title(r.get("title", String.class))
            .description(r.get("description", String.class))
            .startTime(r.get("start_time", LocalDateTime.class))
            .endTime(r.get("end_time", LocalDateTime.class))
            .cost(r.get("cost", BigDecimal.class))
            .isRepeat(r.get("is_repeat", Boolean.class))
            .build();

        task.setExecutedTask(executedTask);
        task.setPaymentJobHistory(paymentJobHistory);

        return task;

    }
}