/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  complex case of generic method call with lambda argument where target
 *          is a wildcard SAM
 * @compile TargetType19.java
 */
import java.util.List;

class TargetType19 {

    interface SAM<X> {
        void f(List<? extends X> i);
    }

    <Z> void call(SAM<? extends Z> s, Z z) {  }

    { call((List<? extends String> p) -> { }, 1); }
}
