/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.MODULE)
@Retention(RetentionPolicy.SOURCE)
public @interface ModuleWarn {

}
