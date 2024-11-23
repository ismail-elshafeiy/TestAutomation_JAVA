
package com.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lifecycle {
    Type type() default Type.METHOD;

    enum Type {
        METHOD,
        CLASS,
        NONE
    }
}
