/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @summary Tests that Lookup can be produced from classes under java.util.concurrent
 * @bug 8154447
 * @compile/module=java.base java/util/concurrent/LookupTester.java
 * @run testng/othervm JavaUtilConcurrentLookupTest
 */

import org.testng.annotations.Test;

import java.util.concurrent.LookupTester;

public class JavaUtilConcurrentLookupTest {

    @Test
    public void testLookup() {
        LookupTester.getLookup();
    }

    @Test
    public void testLookupIn() {
        LookupTester.getLookupIn();
    }
}
