package jp.co.gh.api.payload.request;

import io.swagger.annotations.ApiModelProperty;
import jp.co.gh.api.payload.request.vali.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ログイン処理のリクエストボディ
 */
public record LoginRequest(
        @Email
        @NotBlank
        @ApiModelProperty(value = "ユーザー名及びログインID", example = "hogehoge@example.co.jp", required = true)
        String username,

        @ValidPassword
        @NotBlank
        @ApiModelProperty(value = "パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "GoodHabits!!!", required = true)
        String password
) {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginRequest{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='");
        for (int i = 0, len = this.password.length(); i < len; i++) {
            sb.append("*");
        }
        sb.append('\'').append('}');
        return sb.toString();
    }
}
