package jp.co.havetodo.domain.repo.conv;


import static java.util.Objects.nonNull;

import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import jp.co.havetodo.domain.model.ExecutedTask;
import jp.co.havetodo.domain.model.ExecutedTaskStatus;
import jp.co.havetodo.domain.model.PaymentJobHistory;
import jp.co.havetodo.domain.model.PlannedTask;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class PlannedTaskReadConverter implements Converter<Row, PlannedTask> {

    @Override
    public PlannedTask convert(final Row r) {

        // fetch executed_task table
        final var existsExecutedTaskPlannedTaskIdColumnAndNotNull = r.getMetadata()
            .contains("executed_task_planned_task_id") && nonNull(
            r.get("executed_task_planned_task_id", Long.class));
        final Optional<ExecutedTask> executedTask =
            existsExecutedTaskPlannedTaskIdColumnAndNotNull ? Optional.of(
                new ExecutedTask(r.get("executed_task_planned_task_id", Long.class),
                    r.get("started_time", ZonedDateTime.class),
                    r.get("ended_time", ZonedDateTime.class),
                    new ExecutedTaskStatus(r.get("executed_task_status_name", String.class))))
                : Optional.empty();

        // fetch payment_job_history table
        final var existsPaymentJobHistoryPlannedTaskIdColumnAndNotNull = r.getMetadata()
            .contains("payment_job_history_planned_task_id") && nonNull(
            r.get("payment_job_history_planned_task_id", Long.class));
        final Optional<PaymentJobHistory> paymentJobHistory =
            existsPaymentJobHistoryPlannedTaskIdColumnAndNotNull ? Optional.of(
                new PaymentJobHistory(r.get("payment_job_history_planned_task_id", Long.class),
                    r.get("executed_time", ZonedDateTime.class))) : Optional.empty();

        return PlannedTask.builder()
            .id(r.get("id", Long.class))
            .accountId(r.get("account_id", Long.class))
            .title(r.get("title", String.class))
            .description(r.get("description", String.class))
            .startTime(r.get("start_time", ZonedDateTime.class))
            .endTime(r.get("end_time", ZonedDateTime.class))
            .cost(r.get("cost", BigDecimal.class))
            .isRepeat(r.get("is_repeat", Boolean.class))
            .executedTask(executedTask)
            .paymentJobHistory(paymentJobHistory)
            .build();

    }
}