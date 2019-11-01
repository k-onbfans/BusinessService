package com.accenture.business.handler.reflect;

import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD,METHOD})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface FieldIgnore {
}
