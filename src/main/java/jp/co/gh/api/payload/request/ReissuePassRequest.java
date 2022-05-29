package jp.co.gh.api.payload.request;

import io.swagger.annotations.ApiModelProperty;
import jp.co.gh.api.payload.request.vali.PasswordValueMatch;
import jp.co.gh.api.payload.request.vali.ValidPassword;

import javax.validation.constraints.NotBlank;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match."
        )
})
public record ReissuePassRequest(
        @ValidPassword
        @NotBlank
        @ApiModelProperty(value = "パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "GoodHabits1!!!", required = true)
        String password,

        @ValidPassword
        @NotBlank
        @ApiModelProperty(value = "確認用パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "GoodHabits1!!!", required = true)
        String confirmPassword
) {

    public ReissuePassRequest(
            String password,
            String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReissuePassRequest{");
        sb.append(", password='");
        for (int i = 0, len = this.password.length(); i < len; i++) {
            sb.append("*");
        }
        sb.append('\'').append(", confirmPassword='");
        for (int i = 0, len = this.confirmPassword.length(); i < len; i++) {
            sb.append("*");
        }
        sb.append('\'').append('}');
        return sb.toString();
    }
}
