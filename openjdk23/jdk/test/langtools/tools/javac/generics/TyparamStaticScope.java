/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4994261
 * @summary javac mistakenly reports static use error for enclosing type parameter
 * @author gafter
 *
 * @compile  TyparamStaticScope.java
 */

package typaram.static_.scope;

import java.util.Set;

class JBug<T> {
    abstract class Inner1 implements Set<T> { }
    static class Inner2<U> {
        class Inner3 { U z; }

        abstract class Inner4 implements Set<U> { }
    }
}
