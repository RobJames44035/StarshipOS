/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @compile TestLookup.java TestCls.java
 * @run testng/othervm -ea -esa test.java.lang.invoke.TestLookup
 */
package test.java.lang.invoke;

import org.testng.annotations.Test;

import static java.lang.invoke.MethodHandles.*;

import static org.testng.AssertJUnit.*;

public class TestLookup {

    @Test
    public void testClassLoaderChange() {
        Lookup lookup = lookup();
        assertNotNull(lookup.lookupClass().getClassLoader());
        Lookup lookup2 = lookup.in(Object.class);
        assertNull(lookup2.lookupClass().getClassLoader());
    }

    @Test(expectedExceptions = {ClassNotFoundException.class})
    public void testPublicCannotLoadUserClass() throws IllegalAccessException, ClassNotFoundException {
        Lookup lookup = publicLookup();
        lookup.findClass("test.java.lang.invoke.TestCls");
    }

    @Test
    public void testPublicCanLoadSystemClass() throws IllegalAccessException, ClassNotFoundException {
        Lookup lookup = publicLookup();
        lookup.findClass("java.util.HashMap");
    }

    @Test
    public void testPublicInChangesClassLoader() {
        Lookup lookup = publicLookup();
        // Temporarily exclude until 8148697 is resolved, specifically:
        // "publicLookup changed so that the lookup class is in an unnamed module"
        //assertNull(lookup.lookupClass().getClassLoader());
        Lookup lookup2 = lookup.in(TestCls.class);
        assertNotNull(lookup2.lookupClass().getClassLoader());
    }

}
