package jp.co.havetodo.service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record CreateTaskInputData(
    String title,
    LocalDateTime startTime,
    LocalDateTime endTime,
    BigDecimal cost,
    boolean isRepeat,
    List<Integer> repeatCalendar,
    Date repeatEndTime) {

}
