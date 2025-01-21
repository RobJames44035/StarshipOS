/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.lang.annotation.*;
import java.util.Map;

/*
 * @test
 * @bug 8006775
 * @summary type annotation location: top level blocks
 * @author Werner Dietl
 * @compile TopLevelBlocks.java
 */

class TopLevelBlocks {
    static Object f;

    {
        f = new @A Object();
    }

    static final Object sf;

    static {
        sf = new @A Object();
    }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { }
