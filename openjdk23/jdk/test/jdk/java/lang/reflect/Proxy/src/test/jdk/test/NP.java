/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test;

/*
 * Non-public interface
 */
interface NP {
    void test();

    default int m() {
        return 100;
    }
}
