/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

/**
 * Test LocalDate serialization.
 */
@Test
public class TCKLocalDateSerialization extends AbstractTCKTest {

    private LocalDate TEST_2007_07_15;

    @BeforeMethod
    public void setUp() {
        TEST_2007_07_15 = LocalDate.of(2007, 7, 15);
    }


    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(TEST_2007_07_15);
        assertSerializable(LocalDate.MIN);
        assertSerializable(LocalDate.MAX);
    }

    @Test
    public void test_serialization_format() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(3);
            dos.writeInt(2012);
            dos.writeByte(9);
            dos.writeByte(16);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(LocalDate.of(2012, 9, 16), bytes);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(LocalDate.class);
    }

}
