/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.chrono.serial;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.ThaiBuddhistChronology;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tck.java.time.AbstractTCKTest;

@Test
public class TCKChronologySerialization extends AbstractTCKTest {

    static final int CHRONO_TYPE = 1;            // java.time.chrono.Ser.CHRONO_TYPE

    //-----------------------------------------------------------------------
    // Regular data factory for available calendars
    //-----------------------------------------------------------------------
    @DataProvider(name = "calendars")
    Chronology[][] data_of_calendars() {
        return new Chronology[][]{
                    {HijrahChronology.INSTANCE},
                    {IsoChronology.INSTANCE},
                    {JapaneseChronology.INSTANCE},
                    {MinguoChronology.INSTANCE},
                    {ThaiBuddhistChronology.INSTANCE}};
    }

    //-----------------------------------------------------------------------
    // Test Serialization of Calendars
    //-----------------------------------------------------------------------
    @Test(dataProvider="calendars")
    public void test_chronoSerialization(Chronology chrono) throws Exception {
        assertSerializable(chrono);
    }

    //-----------------------------------------------------------------------
    // Test that serialization produces exact sequence of bytes
    //-----------------------------------------------------------------------
    @Test(dataProvider="calendars")
    private void test_serializationBytes(Chronology chrono) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(CHRONO_TYPE);
            dos.writeUTF(chrono.getId());
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(chrono, bytes);
    }


    //-----------------------------------------------------------------------
    // Regular data factory for names and descriptions of available calendars
    //-----------------------------------------------------------------------
    @DataProvider(name = "invalidSerialformClasses")
    Object[][] invalid_serial_classes() {
        return new Object[][]{
            {IsoChronology.class},
            {JapaneseChronology.class},
            {MinguoChronology.class},
            {ThaiBuddhistChronology.class},
            {HijrahChronology.class},
        };
    }

    @Test(dataProvider="invalidSerialformClasses")
    public void test_invalid_serialform(Class<?> clazz) throws Exception {
        assertNotSerializable(clazz);
    }

}
