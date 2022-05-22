package jp.co.gh.api.payload.request.vali;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * パスワードと確認用パスワードのバリデーション
 */
public class PasswordFieldsValueMatchValidator implements ConstraintValidator<PasswordValueMatch, Object> {

    private String field;
    private String fieldMatch;
    private String message;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(PasswordValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        final var fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        final var fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);

        var isValid = false;
        if (fieldValue != null) {
            isValid = fieldValue.equals(fieldMatchValue);
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldMatch)
                    .addConstraintViolation();
        }

        return isValid;
    }

}
