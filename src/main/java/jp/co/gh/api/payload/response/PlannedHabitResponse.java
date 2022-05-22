package jp.co.gh.api.payload.response;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * 計画済み習慣を返却するときのレスポンスボディ
 */
public record PlannedHabitResponse(
        @ApiModelProperty(value = "習慣ID", example = "1")
        Long id,
        @ApiModelProperty(value = "アカウントID", example = "1")
        Long accountId,
        @ApiModelProperty(value = "習慣のタイトル", example = "ランニング")
        String title,
        @ApiModelProperty(value = "習慣の開始時間", example = "2015-12-15T23:30:59.999+09:00[Asia/Tokyo]")
        ZonedDateTime startTime,
        @ApiModelProperty(value = "習慣の終了時間", example = "2015-12-16T00:30:59.999+09:00[Asia/Tokyo]")
        ZonedDateTime endTime,
        @ApiModelProperty(value = "習慣計画時のお金", example = "1000.0")
        BigDecimal cost,
        @ApiModelProperty(value = "リピートされた習慣か否か", example = "true")
        boolean isRepeat
) {
}
