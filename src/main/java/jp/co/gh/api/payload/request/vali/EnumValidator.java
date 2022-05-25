package jp.co.gh.api.payload.request.vali;

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
    public void initialize(ValidEnum constraintAnnotation) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        return Objects.nonNull(value);
    }

}
