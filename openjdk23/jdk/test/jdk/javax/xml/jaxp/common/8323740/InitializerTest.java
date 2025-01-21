/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import org.testng.annotations.Test;

import java.util.Objects;

/**
 * @test
 *
 * @bug 8323740
 * @summary test that class initializers don't crash
 * @run testng/othervm InitializerTest
 */
public class InitializerTest {

    @Test
    public void testXMLOutputFactory() throws Exception {
        String name = "com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl";
        Objects.requireNonNull(Class.forName(name));
    }
}
