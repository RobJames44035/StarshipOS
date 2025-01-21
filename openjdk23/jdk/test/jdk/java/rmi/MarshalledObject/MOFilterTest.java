/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.MarshalledObject;
import java.util.Objects;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

/* @test
 * @run testng/othervm  MOFilterTest
 *
 * @summary Test MarshalledObject applies ObjectInputFilter
 */
@Test
public class MOFilterTest {

    /**
     * Two cases are tested.
     * The filter = null and a filter set to verify the calls to the filter.
     * @return array objects with test parameters for each test case
     */
    @DataProvider(name = "FilterCases")
    public static Object[][] filterCases() {
        return new Object[][] {
                {true},     // run the test with the filter
                {false},    // run the test without the filter

        };
    }

    /**
     * Test that MarshalledObject inherits the ObjectInputFilter from
     * the stream it was deserialized from.
     */
    @Test(dataProvider="FilterCases")
    static void delegatesToMO(boolean withFilter) {
        try {
            Serializable testobj = Integer.valueOf(5);
            MarshalledObject<Serializable> mo = new MarshalledObject<>(testobj);
            Assert.assertEquals(mo.get(), testobj, "MarshalledObject.get returned a non-equals test object");

            byte[] bytes = writeObjects(mo);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {

                CountingFilter filter1 = new CountingFilter();
                ois.setObjectInputFilter(withFilter ? filter1 : null);
                MarshalledObject<?> actualMO = (MarshalledObject<?>)ois.readObject();
                int count = filter1.getCount();

                actualMO.get();
                int expectedCount = withFilter ? count + 2 : count;
                int actualCount = filter1.getCount();
                Assert.assertEquals(actualCount, expectedCount, "filter called wrong number of times during get()");
            }
        } catch (IOException ioe) {
            Assert.fail("Unexpected IOException", ioe);
        } catch (ClassNotFoundException cnf) {
            Assert.fail("Deserializing", cnf);
        }
    }

    /**
     * Write objects and return a byte array with the bytes.
     *
     * @param objects zero or more objects to serialize
     * @return the byte array of the serialized objects
     * @throws IOException if an exception occurs
     */
    static byte[] writeObjects(Object... objects)  throws IOException {
        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            for (Object o : objects) {
                oos.writeObject(o);
            }
            bytes = baos.toByteArray();
        }
        return bytes;
    }


    static class CountingFilter implements ObjectInputFilter {

        private int count;      // count of calls to the filter

        CountingFilter() {
            count = 0;
        }

        int getCount() {
            return count;
        }

        /**
         * Filter that rejects class Integer and allows others
         *
         * @param filterInfo access to the class, arrayLength, etc.
         * @return {@code STATUS.REJECTED}
         */
        public ObjectInputFilter.Status checkInput(FilterInfo filterInfo) {
            count++;
            return ObjectInputFilter.Status.ALLOWED;
        }
    }

}
