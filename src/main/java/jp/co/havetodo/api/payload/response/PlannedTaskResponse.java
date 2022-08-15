package jp.co.havetodo.api.payload.response;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * 計画済みタスクを返却するときのレスポンスボディ
 */
public record PlannedTaskResponse(
        @ApiModelProperty(value = "タスクID", example = "1")
        Long id,
        @ApiModelProperty(value = "アカウントID", example = "1")
        Long accountId,
        @ApiModelProperty(value = "タスクのタイトル", example = "ランニング")
        String title,
        @ApiModelProperty(value = "タスクの開始時間", example = "2015-12-15T23:30:59.999+09:00[Asia/Tokyo]")
        ZonedDateTime startTime,
        @ApiModelProperty(value = "タスクの終了時間", example = "2015-12-16T00:30:59.999+09:00[Asia/Tokyo]")
        ZonedDateTime endTime,
        @ApiModelProperty(value = "タスク計画時のお金", example = "1000.0")
        BigDecimal cost,
        @ApiModelProperty(value = "リピートされたタスクか否か", example = "true")
        boolean isRepeat
) {
}
