package jp.co.gh.api.payload.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * エラー発生時のレスポンスボディ
 */
public record ApiErrorResponse(
        @ApiModelProperty(value = "エラーメッセージ", example = "400:invalid param, 401:authentication failed, 404:Not found, 500:An error has occurred")
        String message
) {
}
