package com.multitenancy.systems.annotations;

import com.multitenancy.systems.util.generator.FormattedIdGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@IdGeneratorType(FormattedIdGenerator.class)
@Target({FIELD, METHOD})
@Retention(RUNTIME)

public @interface FormattedId {
}
