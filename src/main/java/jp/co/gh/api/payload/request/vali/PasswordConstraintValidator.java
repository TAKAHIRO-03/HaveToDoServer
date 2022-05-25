package jp.co.gh.api.payload.request.vali;

import lombok.SneakyThrows;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Properties;

/**
 * パスワードのバリデーション処理
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private final static String PASSY_PROP = "passay.properties";

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        final var props = new Properties();
        final var inputStream = getClass().getClassLoader().getResourceAsStream(PASSY_PROP);
        props.load(inputStream);
        final var resolver = new PropertiesMessageResolver(props);

        final var validator = new PasswordValidator(resolver, Arrays.asList(

                new LengthRule(8, 64), // length between 8 and 64 characters
                new CharacterRule(EnglishCharacterData.UpperCase, 1), // at least one upper-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1), // at least one lower-case character
                new CharacterRule(EnglishCharacterData.Digit, 1), // at least one digit character
                new CharacterRule(EnglishCharacterData.Special, 1), // at least one symbol (special character)
                new WhitespaceRule(), // no whitespace
                // rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                // rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345)
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
        ));

        final var result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        final var messages = validator.getMessages(result);
        final var messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

}