/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.jvmci.common.testcases;

public class MultipleImplementer1 implements MultipleImplementersInterface {

    @Override
    public void defaultMethod() {
        // empty
    }

    @Override
    public void testMethod() {
        // empty
    }

    @SuppressWarnings("removal")
    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }
}
