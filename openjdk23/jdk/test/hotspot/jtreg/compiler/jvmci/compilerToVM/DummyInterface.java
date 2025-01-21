/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.jvmci.compilerToVM;

interface DummyInterface {
    void dummyFunction();

    default int dummyDefaultFunction(int x, int y) {
        int z = x * y;
        return (int) (Math.cos(x - y + z) * 100);
    }
}
