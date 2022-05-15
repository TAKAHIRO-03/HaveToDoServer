package jp.co.gh.api.payload.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ログイン処理のリクエストボディを定義するクラス
 */
public record LoginRequest(
        @Email
        @NotBlank
        @ApiModelProperty(value = "ユーザー名及びログインID", example = "hogehoge@example.co.jp", required = true)
        String username,

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[.?/-])[a-zA-Z0-9.?/-]{8,64}$")
        @NotBlank
        @ApiModelProperty(value = "パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "GoodHabits!!!", required = true)
        String password
) {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final var returnVal = new StringBuilder();
        returnVal.append("username=");
        returnVal.append(this.username);
        returnVal.append(",password=");
        for (int i = 0, len = this.password.length(); i < len; i++) {
            returnVal.append("*");
        }
        return returnVal.toString();
    }
}
