/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.YearMonth;

/**
 * Test serialization of YearMonth.
 */
@Test
public class TCKYearMonthSerialization extends AbstractTCKTest {

    private YearMonth TEST_2008_06;

    @BeforeMethod
    public void setUp() {
        TEST_2008_06 = YearMonth.of(2008, 6);
    }


    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        assertSerializable(TEST_2008_06);
    }

    @Test
    public void test_serialization_format() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(12);       // java.time.temporal.Ser.YEAR_MONTH_TYPE
            dos.writeInt(2012);
            dos.writeByte(9);
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(YearMonth.of(2012, 9), bytes);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(YearMonth.class);
    }

}
