/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.time.LocalTime;

/**
 * Test LocalTime serialization.
 */
@Test
public class TCKLocalTimeSerialization extends AbstractTCKTest {


    private LocalTime TEST_12_30_40_987654321;


    @BeforeMethod
    public void setUp() {
        TEST_12_30_40_987654321 = LocalTime.of(12, 30, 40, 987654321);
    }


    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(TEST_12_30_40_987654321);
        assertSerializable(LocalTime.MIN);
        assertSerializable(LocalTime.MAX);
    }

    @Test
    public void test_serialization_format_h() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(4);
            dos.writeByte(-1 - 22);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(LocalTime.of(22, 0), bytes);
    }

    @Test
    public void test_serialization_format_hm() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(4);
            dos.writeByte(22);
            dos.writeByte(-1 - 17);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(LocalTime.of(22, 17), bytes);
    }

    @Test
    public void test_serialization_format_hms() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(4);
            dos.writeByte(22);
            dos.writeByte(17);
            dos.writeByte(-1 - 59);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(LocalTime.of(22, 17, 59), bytes);
    }

    @Test
    public void test_serialization_format_hmsn() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(4);
            dos.writeByte(22);
            dos.writeByte(17);
            dos.writeByte(59);
            dos.writeInt(459_000_000);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(LocalTime.of(22, 17, 59, 459_000_000), bytes);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(LocalTime.class);
    }

}
