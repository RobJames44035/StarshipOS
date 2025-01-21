/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.InvalidClassException;

import jdk.internal.access.SharedSecrets;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

/* @test
 * @build CheckArrayTest SerialFilterTest
 * @bug 8203368
 * @modules java.base/jdk.internal.access
 * @run testng CheckArrayTest
 *
 * @summary Test the SharedSecret access to ObjectInputStream.checkArray works
 *      with overridden subclasses.
 */

/**
 * Verify that the SharedSecret access to the OIS checkAccess method
 * does not fail with NPE in the case where ObjectInputStream is subclassed.
 * The checkAccess method is called from various aggregate types in java.util
 * to check array sizes during deserialization via the ObjectInputFilter attached the stream.
 * The filterCheck must be resilent to an InputStream not being available (only the subclass knows).
 */
public class CheckArrayTest {

    @DataProvider(name = "Patterns")
    Object[][] patterns() {
        return new Object[][]{
                new Object[]{"maxarray=10", 10, new String[10]},    // successful
                new Object[]{"maxarray=10", 11, new String[11]},    // exception expected
        };
    }

    /**
     * Test SharedSecrets checkArray with unmodified ObjectInputStream.
     */
    @Test(dataProvider = "Patterns")
    public void normalOIS(String pattern, int arraySize, Object[] array) throws IOException {
        ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(pattern);
        byte[] bytes = SerialFilterTest.writeObjects(array);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            // Check the arraysize against the filter
            try {
                ois.setObjectInputFilter(filter);
                SharedSecrets.getJavaObjectInputStreamAccess()
                        .checkArray(ois, array.getClass(), arraySize);
                Assert.assertTrue(array.length >= arraySize,
                        "Should have thrown InvalidClassException due to array size");
            } catch (InvalidClassException ice) {
                Assert.assertFalse(array.length > arraySize,
                        "Should NOT have thrown InvalidClassException due to array size");
            }
        }
    }

    /**
     * Test SharedSecrets checkArray with an ObjectInputStream subclassed to
     * handle all input stream functions.
     */
    @Test(dataProvider = "Patterns")
    public void subclassedOIS(String pattern, int arraySize, Object[] array) throws IOException {
        byte[] bytes = SerialFilterTest.writeObjects(array);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new MyInputStream(bais)) {
            // Check the arraysize against the filter
            ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(pattern);
            ois.setObjectInputFilter(filter);
            SharedSecrets.getJavaObjectInputStreamAccess()
                    .checkArray(ois, array.getClass(), arraySize);
            Assert.assertTrue(array.length >= arraySize,
                    "Should have thrown InvalidClassException due to array size");
        } catch (InvalidClassException ice) {
            Assert.assertFalse(array.length > arraySize,
                    "Should NOT have thrown InvalidClassException due to array size");
        }
    }

    /**
     * Subclass OIS to disable all input stream functions of the OIS.
     */
    static class MyInputStream extends ObjectInputStream {
        MyInputStream(InputStream is) throws IOException {
            super();
        }

        public void close() {
        }
    }
}
