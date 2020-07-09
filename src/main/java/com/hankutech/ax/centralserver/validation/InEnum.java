package com.hankutech.ax.centralserver.validation;

import com.hankutech.ax.centralserver.validation.validator.InEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验枚举值
 * @author ZhangXi
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InEnumValidator.class)
public @interface InEnum {

    Class<? extends Enum> enumClass();

    String message() default "{app.validation.constraints.InEnum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
