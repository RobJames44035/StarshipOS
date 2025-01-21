/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.time.Year;

/**
 * Test Year serialization.
 */
@Test
public class TCKYearSerialization extends AbstractTCKTest {

    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(Year.of(2));
        assertSerializable(Year.of(0));
        assertSerializable(Year.of(-2));
    }

    @Test
    public void test_serialization_format() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(11);       // java.time.temporal.Ser.YEAR_TYPE
            dos.writeInt(2012);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(Year.of(2012), bytes);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(Year.class);
    }

}
