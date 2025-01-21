/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8020997
 * @summary Cannot compile class with repeating annotation
 * @compile CannotCompileRepeatedAnnoTest.java
 */

import java.lang.annotation.*;

@Anno(req = true)
@Anno
public class CannotCompileRepeatedAnnoTest {
}

@Repeatable(Container.class)
@interface Anno {
    boolean req() default false;
}

@interface Container{
    Anno[] value();
}
