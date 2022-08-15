package jp.co.havetodo.api.payload.request.vali;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Documented
public @interface FieldsValueMatch {

    String message() default "Fields values don't match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldMatch();

    @Target({TYPE})
    @Retention(RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}