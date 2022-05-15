package jp.co.gh.api.payload.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * エラー発生時のレスポンスボディを定義するクラス
 */
public record ApiErrorResponse(
        @ApiModelProperty(value = "エラーメッセージ", example = "エラーが発生しました")
        String message
) {
}
