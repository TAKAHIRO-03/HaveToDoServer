package jp.co.havetodo.api.payload.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import jp.co.havetodo.api.payload.request.vali.FieldsValueMatch;
import jp.co.havetodo.api.payload.request.vali.ValidPassword;

@FieldsValueMatch.List({
    @FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match."
    )
})
public record AccountPasswordRequest(

    @ValidPassword
    @NotBlank
    @ApiModelProperty(value = "現在使っているパスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "HaveTodo1!!!", required = true)
    String currentPassword,

    @ValidPassword
    @NotBlank
    @ApiModelProperty(value = "パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "HaveTodo1!!!", required = true)
    String password,

    @ValidPassword
    @NotBlank
    @ApiModelProperty(value = "確認用パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "HaveTodo1!!!", required = true)
    String confirmPassword
) {

    public AccountPasswordRequest(
        final String currentPassword,
        final String password,
        final String confirmPassword) {
        this.currentPassword = currentPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountPasswordRequest{");
        sb.append("currentPassword='");
        for (int i = 0, len = this.currentPassword.length(); i < len; i++) {
            sb.append("*");
        }
        sb.append('\'');
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
