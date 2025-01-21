/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/compiler/complog/uninit/uninit013.
 * VM Testbase keywords: [quick, jit]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build vm.compiler.complog.share.LogCompilationTest
 *        vm.compiler.complog.uninit.uninit013.uninit013
 *        vm.compiler.complog.uninit.UninitializedTrapCounter
 * @run main/othervm
 *      vm.compiler.complog.share.LogCompilationTest
 *      -testedJava ${test.jdk}/bin/java
 *      -testClass vm.compiler.complog.uninit.uninit013.uninit013
 *      -parserClass vm.compiler.complog.uninit.UninitializedTrapCounter
 *      -parserOptions "-classFilter=.*uninit.*"
 */

package vm.compiler.complog.uninit.uninit013;
import vm.compiler.complog.share.Constants;

/**
 * Provoke OSR for loop which mutate fields of uninitialized class.
 */

public class uninit013 {

    Object field = null;
    static Object staticField = null;

    static {
        uninit013 u = new uninit013();

        for(int i = 0; i<Constants.LOOP_ITERATIONS; i++) {
            u.field = new Object();
            uninit013.staticField = new Object();
        }
    }

    public static void main(String args[]) {

    }

}
