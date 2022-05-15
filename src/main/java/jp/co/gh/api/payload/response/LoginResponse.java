package jp.co.gh.api.payload.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * ログイン処理のレスポンスボディを定義するクラス
 */
public record LoginResponse(
        @ApiModelProperty(value = "トークンのスキーム", example = "Bearer")
        String tokenType,
        @ApiModelProperty(value = "アクセストークン（JWT）", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ")
        String token,
        @ApiModelProperty(value = "リフレッシュトークン（UUID）", example = "ede66c43-9b9d-4222-93ed-5f11c96e08e2")
        String refreshToken,
        @ApiModelProperty(value = "ユーザーID", example = "0")
        Long id,
        @ApiModelProperty(value = "ユーザー名及びログインID", example = "hogehoge@example.co.jp")
        String username,
        @ApiModelProperty(value = "ユーザーの権限", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
        List<String> roles
) {
    public String tokenType() {
        return "Bearer";
    }
}