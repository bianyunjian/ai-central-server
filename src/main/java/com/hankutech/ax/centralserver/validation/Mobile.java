package com.hankutech.ax.centralserver.validation;

import com.hankutech.ax.centralserver.validation.validator.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义参数校验：手机号
 *
 * @author ZhangXi
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

    String message() default "{app.validator.constraints.mobile.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
