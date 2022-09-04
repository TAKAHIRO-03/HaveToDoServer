package jp.co.havetodo.api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jp.co.havetodo.api.payload.request.vali.ValidEnum;
import org.springframework.util.CollectionUtils;

/**
 * タスクを登録する時のリクエストボディ
 */
public record TaskRequest(
    @NotBlank
    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "タスクのタイトル", example = "ランニング", required = true)
    String title,

    @NotBlank
    @Size(min = 1, max = 1000)
    @ApiModelProperty(value = "タスクの説明", example = "ランニングを行います")
    String description,

    @NotNull
    @FutureOrPresent
    @ApiModelProperty(value = "タスクの開始時間", example = "2015-12-15T23:30:59.999", required = true)
    LocalDateTime startTime,

    @NotNull
    @FutureOrPresent
    @ApiModelProperty(value = "タスクの終了時間", example = "2015-12-16T00:30:59.999", required = true)
    LocalDateTime endTime,

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "10000")
    @Digits(integer = 5, fraction = 1)
    @ApiModelProperty(value = "タスク計画時のお金", example = "1000.0", required = true)
    BigDecimal cost,

    @ApiModelProperty(value = "リピートの有無", example = "true")
    boolean isRepeat,

    @ApiModelProperty(value = "曜日に対応した数字。日:1, 月:2, 火:3, 水:4, 木:5, 金:6, 土:7", example = "[1]")
    @Valid
    Set<@ValidEnum DayOfWeek> repeatDayOfWeek,

    @Future
    @ApiModelProperty(value = "いつまでリピートするかの最後の日付", example = "2015-12-31")
    LocalDate repeatEndDate
) {

    @JsonIgnore
    @AssertTrue(message = "when \"isRepeat\" is true, \"repeatDayOfWeek\" must not be empty.")
    public boolean isRepeatDayOfWeekNotEmpty() {
        if (!this.isRepeat/* when is "isRepeat" false, return true */) {
            return true;
        }
        return !CollectionUtils.isEmpty(this.repeatDayOfWeek);
    }

    @JsonIgnore
    @AssertTrue(message = "when \"isRepeat\" is true, \"repeatEndDate\" must not be null.")
    public boolean isRepeatEndDateNotNull() {
        if (!this.isRepeat/* when is "isRepeat" false, return true */) {
            return true;
        }
        return Objects.nonNull(this.repeatEndDate);
    }

}
