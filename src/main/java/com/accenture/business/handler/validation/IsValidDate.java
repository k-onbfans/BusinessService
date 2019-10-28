package com.accenture.business.handler.validation;


import com.accenture.business.utils.DateUtil;

import javax.validation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = IsValidDate.ValidChecker.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface IsValidDate {

    boolean comparable() default true;

    boolean future() default true;

    String message() default "Date is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ValidChecker implements ConstraintValidator<IsValidDate,Date>{

        private boolean comparable;

        private boolean future;

        @Override
        public void initialize(IsValidDate constraintAnnotation) {

            this.future = constraintAnnotation.future();

            this.comparable = constraintAnnotation.comparable();

        }

        @Override
        public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
            if(comparable){
                Date now = new Date();
                if(future) {
                    if(date.after(now)) {
                        return true;
                    }else {
                        return false;
                    }
                } else {
                    return true;
                }
            }
            return true;
        }
    }
}