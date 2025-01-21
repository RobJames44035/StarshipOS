/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package vm.share.options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * This annotation marks fields of type Map<String, String>,
 * where the name=>value map of given options is placed by the framework
 *
 * should be stored
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionsMap { }
