/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/compiler/complog/uninit/uninit001.
 * VM Testbase keywords: [quick, jit]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build vm.compiler.complog.share.LogCompilationTest
 *        vm.compiler.complog.uninit.uninit001.uninit001
 *        vm.compiler.complog.uninit.UninitializedTrapCounter
 * @run main/othervm
 *      vm.compiler.complog.share.LogCompilationTest
 *      -testedJava ${test.jdk}/bin/java
 *      -testClass vm.compiler.complog.uninit.uninit001.uninit001
 *      -parserClass vm.compiler.complog.uninit.UninitializedTrapCounter
 *      -parserOptions "-classFilter=.*uninit.*"
 */

package vm.compiler.complog.uninit.uninit001;

import vm.compiler.complog.share.*;

/**
 * Provoke osr and compilation of method with
 * 'new' calls of uninitialized class.
 */

public class uninit001 {

    static {
        for(int i = 0; i<Constants.LOOP_ITERATIONS; i++) {
            uninit001 u = new uninit001();
            Object o = u.copy();
        }
    }

    public Object copy() {
        return new uninit001();
    }

    public static void main(String args[]) {

    }
}
