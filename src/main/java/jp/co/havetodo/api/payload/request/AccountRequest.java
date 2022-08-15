package jp.co.havetodo.api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jp.co.havetodo.api.payload.request.vali.FieldsValueMatch;
import jp.co.havetodo.api.payload.request.vali.ValidPassword;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match."
        ),
        @FieldsValueMatch(
                field = "username",
                fieldMatch = "confirmUsername",
                message = "Username do not match."
        )
})
public record AccountRequest(

        @Email
        @NotBlank
        @ApiModelProperty(value = "ユーザー名及びログインID", example = "hogehoge@example.co.jp", required = true)
        String username,

        @Email
        @NotBlank
        @ApiModelProperty(value = "確認用ユーザー名及びログインID", example = "hogehoge@example.co.jp", required = true)
        String confirmUsername,

        @ValidPassword
        @NotBlank
        @ApiModelProperty(value = "パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "HaveTodo1!!!", required = true)
        String password,

        @ValidPassword
        @NotBlank
        @ApiModelProperty(value = "パスワード。8文字以上64文字以下で、半角英数字、大文字のアルファベット、記号をそれぞれ1文字以上含まれていること", example = "HaveTodo1!!!", required = true)
        String confirmPassword,

        @JsonIgnore
        String timezonesName,

        @JsonIgnore
        String currencyIsoCode,

        @AssertTrue
        @ApiModelProperty(value = "利用規約のチェック", example = "true", required = true)
        boolean acceptTerms
) {

    public AccountRequest(
            final String username,
            final String confirmUsername,
            final String password,
            final String confirmPassword,
            final String timezonesName,
            final String currencyIsoCode,
            final boolean acceptTerms) {
        this.username = username;
        this.confirmUsername = confirmUsername;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.timezonesName = "Asia/Tokyo"; /* Fixed for Japan. Until multilingual support*/
        this.currencyIsoCode = "JPY"; /* Fixed for Japan. Until multilingual support*/
        this.acceptTerms = acceptTerms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountRequest{");
        sb.append("username='").append(username).append('\'');
        sb.append("confirmUsername='").append(confirmUsername).append('\'');
        sb.append(", password='");
        for (int i = 0, len = this.password.length(); i < len; i++) {
            sb.append("*");
        }
        sb.append('\'').append(", confirmPassword='");
        for (int i = 0, len = this.confirmPassword.length(); i < len; i++) {
            sb.append("*");
        }
        sb.append('\'').append(", timezonesName='").append(timezonesName).append('\'');
        sb.append(", currencyIsoCode='").append(currencyIsoCode).append('\'');
        sb.append(", acceptTerms=").append(acceptTerms);
        sb.append('}');
        return sb.toString();
    }
}
