package com.connectivity.Model.Dao.Markers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String columnName() default "null";
    String columnType() default "null";
    boolean isNotNull() default false;
    boolean isUnique() default false;
    boolean isAutoIncrement() default false;
}
