/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.*;

@Target({
    ElementType.TYPE_PARAMETER,
})
@interface Container {
  DefaultTargetTypeParameter[] value();
}

@Repeatable(Container.class)
public @interface DefaultTargetTypeParameter {}
