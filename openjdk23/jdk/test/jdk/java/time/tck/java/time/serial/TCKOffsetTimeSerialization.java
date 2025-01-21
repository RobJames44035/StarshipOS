/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.time.OffsetTime;
import java.time.ZoneOffset;

/**
 * Test OffsetTime serialization.
 */
@Test
public class TCKOffsetTimeSerialization extends AbstractTCKTest {

    private static final ZoneOffset OFFSET_PONE = ZoneOffset.ofHours(1);
    private OffsetTime TEST_11_30_59_500_PONE;

    @BeforeMethod
    public void setUp() {
        TEST_11_30_59_500_PONE = OffsetTime.of(11, 30, 59, 500, OFFSET_PONE);
    }



    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(TEST_11_30_59_500_PONE);
        assertSerializable(OffsetTime.MIN);
        assertSerializable(OffsetTime.MAX);
    }

    @Test
    public void test_serialization_format() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(9);       // java.time.Ser.OFFSET_TIME_TYPE
            dos.writeByte(22);
            dos.writeByte(17);
            dos.writeByte(59);
            dos.writeInt(464_000_000);
            dos.writeByte(4);  // quarter hours stored: 3600 / 900
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(OffsetTime.of(22, 17, 59, 464_000_000, ZoneOffset.ofHours(1)), bytes);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(OffsetTime.class);
    }

}
