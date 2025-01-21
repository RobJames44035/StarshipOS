/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/compiler/complog/uninit/uninit005.
 * VM Testbase keywords: [quick, jit]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build vm.compiler.complog.share.LogCompilationTest
 *        vm.compiler.complog.uninit.uninit005.uninit005
 *        vm.compiler.complog.uninit.UninitializedTrapCounter
 * @run main/othervm
 *      vm.compiler.complog.share.LogCompilationTest
 *      -testedJava ${test.jdk}/bin/java
 *      -testClass vm.compiler.complog.uninit.uninit005.uninit005
 *      -parserClass vm.compiler.complog.uninit.UninitializedTrapCounter
 *      -parserOptions "-classFilter=.*uninit.*"
 */

package vm.compiler.complog.uninit.uninit005;

import vm.compiler.complog.share.*;

/**
 * Provoke compilation of uninitialized class's method
 * with 'new' call of the same class.
 */

public class uninit005 {
    static {
        uninit005 u = new uninit005();
        Object o = u.recursiveMethod(Constants.RECURSION_DEPTH);
    }

    public Object recursiveMethod(int i) {
        if(i <= 0) {
            return new uninit005();
        } else {
            Object o = null;
            o = recursiveMethod(i-1);
            o = recursiveMethod(i-1);
            return o;
        }
    }

    public static void main(String args[]) {

    }
}
