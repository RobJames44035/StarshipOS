/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg;

import static pkg.A.Outer.Inner;

@annotations.TriggersComplete(of=A.Outer.class, at=annotations.Phase.IMPORTS)
class A {
    @annotations.TriggersComplete(of=B.class, at=annotations.Phase.HIERARCHY)
    @annotations.TriggersComplete(of=B.Inner.class, at=annotations.Phase.HEADER)
    static class Outer<X extends Inner> extends B { }
}

class B {
    static class Inner {}
}
