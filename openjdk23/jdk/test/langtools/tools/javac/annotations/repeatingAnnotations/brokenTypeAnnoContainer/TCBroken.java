/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.annotation.*;

@Target(ElementType.TYPE_USE)
@interface TC {
    T[] value();
    int foo();
}
