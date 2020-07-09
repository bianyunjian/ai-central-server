package com.hankutech.ax.centralserver.validation.validator;

import com.hankutech.ax.centralserver.validation.InEnum;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 枚举类校验器
 * @author ZhangXi
 */
@Slf4j
public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    private Class enumClass;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        if (null == o) {
            log.error("待验证枚举对象为空");
            return false;
        }

        if (! enumClass.isInstance(o)) {
            log.error("待校验枚举对象：{} 不符合预定义类型：{}", o.getClass().getName(), enumClass.getName());
            return false;
        }

        List enumList = getEnumList(enumClass);
        Enum value = (Enum) o;
        for (Object one : enumList) {
            Enum oneEnum = (Enum) one;
            if (value.compareTo(oneEnum) == 0) {
                return true;
            }
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    private static <E extends Enum<E>> List<E> getEnumList(Class<E> enumClass) {
        return new ArrayList(Arrays.asList(enumClass.getEnumConstants()));
    }

}
