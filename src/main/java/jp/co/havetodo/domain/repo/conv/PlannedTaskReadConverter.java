package jp.co.havetodo.domain.repo.conv;


import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import jp.co.havetodo.domain.model.ExecutedTask;
import jp.co.havetodo.domain.model.PaymentJobHistory;
import jp.co.havetodo.domain.model.PlannedTask;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class PlannedTaskReadConverter implements Converter<Row, PlannedTask> {

    @Override
    public PlannedTask convert(final Row r) {

        final Optional<ExecutedTask> executedTask = Optional.empty();

        final Optional<PaymentJobHistory> paymentJobHistory = Optional.empty();

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