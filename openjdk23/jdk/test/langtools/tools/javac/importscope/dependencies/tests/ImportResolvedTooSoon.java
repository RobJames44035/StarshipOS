/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg;

import annotations.*;

import static pkg.B.SubInner.Foo;

@TriggersComplete(of=A.class, at=Phase.HIERARCHY)
@TriggersComplete(of=B.SubInner.class, at=Phase.IMPORTS)
class B extends A {
    @TriggersComplete(of=A.Inner.class, at=Phase.HIERARCHY)
    static class SubInner extends Inner { }
}

class A {
     static class Inner {
         static class Foo { }
     }
}
