/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.time.LocalDateTime;

/**
 * Test serialization of LocalDateTime.
 */
@Test
public class TCKLocalDateTimeSerialization extends AbstractTCKTest {

    private LocalDateTime TEST_2007_07_15_12_30_40_987654321 = LocalDateTime.of(2007, 7, 15, 12, 30, 40, 987654321);

    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(TEST_2007_07_15_12_30_40_987654321);
        assertSerializable(LocalDateTime.MIN);
        assertSerializable(LocalDateTime.MAX);
    }

    @Test
    public void test_serialization_format() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(5);
            dos.writeInt(2012);
            dos.writeByte(9);
            dos.writeByte(16);
            dos.writeByte(22);
            dos.writeByte(17);
            dos.writeByte(59);
            dos.writeInt(459_000_000);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(LocalDateTime.of(2012, 9, 16, 22, 17, 59, 459_000_000), bytes);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(LocalDateTime.class);
    }

}
