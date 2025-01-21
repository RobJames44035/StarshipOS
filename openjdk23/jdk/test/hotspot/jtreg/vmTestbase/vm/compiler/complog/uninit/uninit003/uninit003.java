/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/compiler/complog/uninit/uninit003.
 * VM Testbase keywords: [quick, jit]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build vm.compiler.complog.share.LogCompilationTest
 *        vm.compiler.complog.uninit.uninit003.uninit003
 *        vm.compiler.complog.uninit.UninitializedTrapCounter
 * @run main/othervm
 *      vm.compiler.complog.share.LogCompilationTest
 *      -testedJava ${test.jdk}/bin/java
 *      -testClass vm.compiler.complog.uninit.uninit003.uninit003
 *      -parserClass vm.compiler.complog.uninit.UninitializedTrapCounter
 *      -parserOptions "-classFilter=.*uninit.*"
 */

package vm.compiler.complog.uninit.uninit003;

import vm.compiler.complog.share.*;

/**
 * Provoke OSR for loop without 'new' call of uninitialized class
 * in method class from initializer.
 */

public class uninit003 {

    static {
        uninit003 u = new uninit003();
        Object o = u.osr();
    }

    public Object osr() {
        Object o = null;
        for(int i = 0; i<Constants.LOOP_ITERATIONS; i++) {
            o = new Object();
        }
        return o;
    }

    public static void main(String args[]) {

    }
}
