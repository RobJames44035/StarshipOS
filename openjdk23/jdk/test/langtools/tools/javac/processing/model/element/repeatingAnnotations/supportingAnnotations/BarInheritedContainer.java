/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.lang.annotation.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(BarInheritedContainerContainer.class)
public @interface BarInheritedContainer {
    BarInherited[] value();
}
