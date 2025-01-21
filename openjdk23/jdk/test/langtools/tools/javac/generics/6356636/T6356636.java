/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6356636
 * @summary Compiler fails to read signatures of inner classes with non-generic outer instance leading to crash
 * @compile a/AbstractFoo.java a/Bar.java
 * @compile T6356636.java
 */

import a.*;

public final class T6356636 extends AbstractFoo implements Bar {
    public InnerFoo<String> getInnerFoo() {
        return null;
    }
}
