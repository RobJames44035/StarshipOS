/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/compiler/complog/uninit/uninit006.
 * VM Testbase keywords: [quick, jit]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build vm.compiler.complog.share.LogCompilationTest
 *        vm.compiler.complog.uninit.uninit006.uninit006
 *        vm.compiler.complog.uninit.UninitializedTrapCounter
 * @run main/othervm
 *      vm.compiler.complog.share.LogCompilationTest
 *      -testedJava ${test.jdk}/bin/java
 *      -testClass vm.compiler.complog.uninit.uninit006.uninit006
 *      -parserClass vm.compiler.complog.uninit.UninitializedTrapCounter
 *      -parserOptions "-classFilter=.*uninit.*"
 */

package vm.compiler.complog.uninit.uninit006;

import vm.compiler.complog.share.*;
/**
 * Provoke compilation of uninitialized class's method
 * without 'new' call of the same class.
 */

public class uninit006 {

    static {
        uninit006 u = new uninit006();
        Object o = u.recursiveMethod(Constants.RECURSION_DEPTH);
    }

    public Object recursiveMethod(int i ) {
        if(i <= 0) {
            return new Object();
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
