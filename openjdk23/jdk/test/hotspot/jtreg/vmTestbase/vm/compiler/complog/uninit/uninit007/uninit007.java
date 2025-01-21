/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase vm/compiler/complog/uninit/uninit007.
 * VM Testbase keywords: [quick, jit]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build vm.compiler.complog.share.LogCompilationTest
 *        vm.compiler.complog.uninit.uninit007.uninit007
 *        vm.compiler.complog.uninit.UninitializedTrapCounter
 * @run main/othervm
 *      vm.compiler.complog.share.LogCompilationTest
 *      -testedJava ${test.jdk}/bin/java
 *      -testClass vm.compiler.complog.uninit.uninit007.uninit007
 *      -parserClass vm.compiler.complog.uninit.UninitializedTrapCounter
 *      -parserOptions "-classFilter=.*uninit.*"
 */

package vm.compiler.complog.uninit.uninit007;
import vm.compiler.complog.share.Constants;

/**
 * Provoke OSR in initializer of sub class with it's super class method call.
 * Super class's method contains 'new' call of it's sub class.
 */

public class uninit007 extends uninit007s {

    static {
        for(int i = 0; i<Constants.LOOP_ITERATIONS; i++) {
            uninit007s u = new uninit007s();
            Object o = u.copy();
        }
    }

    public static void main(String args[]) {

    }

}

class uninit007s {

    public Object copy() {
        return new uninit007();
    }

}
