package jp.co.havetodo.api.payload.request.vali;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Enum型のバリデーション
 */
public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final ValidEnum constraintAnnotation) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final Enum value, final ConstraintValidatorContext context) {
        return Objects.nonNull(value);
    }

}
