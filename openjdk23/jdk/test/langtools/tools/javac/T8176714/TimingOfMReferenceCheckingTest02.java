/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8176714
 * @summary javac is wrongly assuming that field JCMemberReference.overloadKind has been assigned to
 * @compile TimingOfMReferenceCheckingTest02.java
 */

import java.util.function.*;

public class TimingOfMReferenceCheckingTest02 {
    <Z> void g(Consumer<Z> fzr, Z z) {}
   <T> T f(T t) { return null; }

   void test(boolean cond) {
        g(cond ?
            f(cond ?
                this::m :
                this::m) :
            this::m, "");
    }

    void m(String s) {}
    void m(Integer i) {}
}
