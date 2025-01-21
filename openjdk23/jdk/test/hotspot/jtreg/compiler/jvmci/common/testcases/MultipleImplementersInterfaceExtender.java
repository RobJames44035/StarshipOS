/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.jvmci.common.testcases;

public interface MultipleImplementersInterfaceExtender
        extends MultipleImplementersInterface {
    // provide default implementation for parent interface
    @Override
    default void testMethod() {
        // empty
    }
}
