/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import static java.lang.annotation.ElementType.*;

@java.lang.annotation.Target({FIELD})
@interface foo {
}

@foo
public class WrongTarget {
}
