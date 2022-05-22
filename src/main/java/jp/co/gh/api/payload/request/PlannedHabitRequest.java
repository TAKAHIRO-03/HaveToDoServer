package jp.co.gh.api.payload.request;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * 計画済み習慣を登録する時のリクエストボディ
 */
public record PlannedHabitRequest(
        @NotBlank
        @Size(min = 1, max = 100)
        @ApiModelProperty(value = "習慣のタイトル", example = "ランニング", required = true)
        String title,

        @NotNull
        @FutureOrPresent
        @ApiModelProperty(value = "習慣の開始時間", example = "2015-12-15T23:30:59.999+09:00[Asia/Tokyo]", required = true)
        ZonedDateTime startTime,

        @NotNull
        @FutureOrPresent
        @ApiModelProperty(value = "習慣の終了時間", example = "2015-12-16T00:30:59.999+09:00[Asia/Tokyo]", required = true)
        ZonedDateTime endTime,

        @NotNull
        @DecimalMin(value = "0")
        @DecimalMax(value = "10000")
        @Digits(integer = 5, fraction = 1)
        @ApiModelProperty(value = "習慣計画時のお金", example = "1000.0", required = true)
        BigDecimal cost,

        @ApiModelProperty(value = "リピートの有無", example = "true")
        boolean isRepeat,

        @ApiModelProperty(value = "曜日に対応した数字。日:1, 月:2, 火:3, 水:4, 木:5, 金:6, 土:7", example = "[1]")
        @Valid
        List<@Min(1) @Max(7) Integer> repeatCalendar,

        @Future
        @ApiModelProperty(value = "いつまでリピートするかの最後の日付", example = "2015-12-31")
        Date repeatEndTime
) {

    @AssertTrue
    public boolean isRepeatCalendarNotEmpty() {
        if (!this.isRepeat/* when is "isRepeat" false, return true */) {
            return true;
        }
        return !CollectionUtils.isEmpty(this.repeatCalendar);
    }

    @AssertTrue
    public boolean isRepeatEndTimeNotNull() {
        if (!this.isRepeat/* when is "isRepeat" false, return true */) {
            return true;
        }
        return Objects.nonNull(this.repeatEndTime);
    }

}
