/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.serial;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamConstants;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Test serialization of ZoneId.
 */
@Test
public class TCKZoneIdSerialization extends AbstractTCKTest {

    //-----------------------------------------------------------------------
    @Test
    public void test_serialization() throws Exception {
        assertSerializable(ZoneId.of("Europe/London"));
        assertSerializable(ZoneId.of("America/Chicago"));
    }

    @Test
    public void test_serialization_format() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos) ) {
            dos.writeByte(7);
            dos.writeUTF("Europe/London");
        }
        byte[] bytes = baos.toByteArray();
        assertSerializedBySer(ZoneId.of("Europe/London"), bytes);
    }

    @Test
    public void test_deserialization_lenient_characters() throws Exception {
        // an ID can be loaded without validation during deserialization
        String id = "QWERTYUIOPASDFGHJKLZXCVBNM~/._+-";
        ZoneId deser = deserialize(id);
        // getId, equals, hashCode, toString and normalized are OK
        assertEquals(deser.getId(), id);
        assertEquals(deser.toString(), id);
        assertEquals(deser, deser);
        assertEquals(deser.hashCode(), deser.hashCode());
        assertEquals(deser.normalized(), deser);
        // getting the rules is not
        try {
            deser.getRules();
            fail();
        } catch (ZoneRulesException ex) {
            // expected
        }
    }

    @Test(expectedExceptions=DateTimeException.class)
    public void test_deserialization_lenient_badCharacters() throws Exception {
        // an ID can be loaded without validation during deserialization
        // but there is a check to ensure the ID format is valid
        deserialize("|!?");
    }

    @Test(dataProvider="offsetBasedValid")
    public void test_deserialization_lenient_offsetNotAllowed_noPrefix(String input, String resolvedId) throws Exception {
        ZoneId deserialized = deserialize(input);
        assertEquals(deserialized, ZoneId.of(input));
        assertEquals(deserialized, ZoneId.of(resolvedId));
    }

    @Test(dataProvider="offsetBasedValidPrefix")
    public void test_deserialization_lenient_offsetNotAllowed_prefixUTC(String input, String resolvedId, String offsetId) throws Exception {
        ZoneId deserialized = deserialize("UTC" + input);
        assertEquals(deserialized, ZoneId.of("UTC" + input));
        assertEquals(deserialized, ZoneId.of("UTC" + resolvedId));
    }

    @Test(dataProvider="offsetBasedValidPrefix")
    public void test_deserialization_lenient_offsetNotAllowed_prefixGMT(String input, String resolvedId, String offsetId) throws Exception {
        ZoneId deserialized = deserialize("GMT" + input);
        assertEquals(deserialized, ZoneId.of("GMT" + input));
        assertEquals(deserialized, ZoneId.of("GMT" + resolvedId));
    }

    @Test(dataProvider="offsetBasedValidPrefix")
    public void test_deserialization_lenient_offsetNotAllowed_prefixUT(String input, String resolvedId, String offsetId) throws Exception {
        ZoneId deserialized = deserialize("UT" + input);
        assertEquals(deserialized, ZoneId.of("UT" + input));
        assertEquals(deserialized, ZoneId.of("UT" + resolvedId));
    }

    private ZoneId deserialize(String id) throws Exception {
        String serClass = ZoneId.class.getPackage().getName() + ".Ser";
        long serVer = getSUID(ZoneId.class);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeShort(ObjectStreamConstants.STREAM_MAGIC);
            dos.writeShort(ObjectStreamConstants.STREAM_VERSION);
            dos.writeByte(ObjectStreamConstants.TC_OBJECT);
            dos.writeByte(ObjectStreamConstants.TC_CLASSDESC);
            dos.writeUTF(serClass);
            dos.writeLong(serVer);
            dos.writeByte(ObjectStreamConstants.SC_EXTERNALIZABLE | ObjectStreamConstants.SC_BLOCK_DATA);
            dos.writeShort(0);  // number of fields
            dos.writeByte(ObjectStreamConstants.TC_ENDBLOCKDATA);  // end of classdesc
            dos.writeByte(ObjectStreamConstants.TC_NULL);  // no superclasses
            dos.writeByte(ObjectStreamConstants.TC_BLOCKDATA);
            dos.writeByte(1 + 2 + id.length());  // length of data (1 byte + 2 bytes UTF length + 32 bytes UTF)
            dos.writeByte(7);  // ZoneId
            dos.writeUTF(id);
            dos.writeByte(ObjectStreamConstants.TC_ENDBLOCKDATA);  // end of blockdata
        }
        ZoneId deser = null;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            deser = (ZoneId) ois.readObject();
        }
        return deser;
    }

    //-----------------------------------------------------------------------
    // regular factory and .normalized()
    //-----------------------------------------------------------------------
    @DataProvider(name="offsetBasedValid")
    Object[][] data_offsetBasedValid() {
        return new Object[][] {
                {"Z", "Z"},
                {"+0", "Z"},
                {"-0", "Z"},
                {"+00", "Z"},
                {"+0000", "Z"},
                {"+00:00", "Z"},
                {"+000000", "Z"},
                {"+00:00:00", "Z"},
                {"-00", "Z"},
                {"-0000", "Z"},
                {"-00:00", "Z"},
                {"-000000", "Z"},
                {"-00:00:00", "Z"},
                {"+5", "+05:00"},
                {"+01", "+01:00"},
                {"+0100", "+01:00"},
                {"+01:00", "+01:00"},
                {"+010000", "+01:00"},
                {"+01:00:00", "+01:00"},
                {"+12", "+12:00"},
                {"+1234", "+12:34"},
                {"+12:34", "+12:34"},
                {"+123456", "+12:34:56"},
                {"+12:34:56", "+12:34:56"},
                {"-02", "-02:00"},
                {"-5", "-05:00"},
                {"-0200", "-02:00"},
                {"-02:00", "-02:00"},
                {"-020000", "-02:00"},
                {"-02:00:00", "-02:00"},
        };
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="offsetBasedValidPrefix")
    Object[][] data_offsetBasedValidPrefix() {
        return new Object[][] {
                {"", "", "Z"},
                {"+0", "", "Z"},
                {"-0", "", "Z"},
                {"+00", "", "Z"},
                {"+0000", "", "Z"},
                {"+00:00", "", "Z"},
                {"+000000", "", "Z"},
                {"+00:00:00", "", "Z"},
                {"-00", "", "Z"},
                {"-0000", "", "Z"},
                {"-00:00", "", "Z"},
                {"-000000", "", "Z"},
                {"-00:00:00", "", "Z"},
                {"+5", "+05:00", "+05:00"},
                {"+01", "+01:00", "+01:00"},
                {"+0100", "+01:00", "+01:00"},
                {"+01:00", "+01:00", "+01:00"},
                {"+010000", "+01:00", "+01:00"},
                {"+01:00:00", "+01:00", "+01:00"},
                {"+12", "+12:00", "+12:00"},
                {"+1234", "+12:34", "+12:34"},
                {"+12:34", "+12:34", "+12:34"},
                {"+123456", "+12:34:56", "+12:34:56"},
                {"+12:34:56", "+12:34:56", "+12:34:56"},
                {"-02", "-02:00", "-02:00"},
                {"-5", "-05:00", "-05:00"},
                {"-0200", "-02:00", "-02:00"},
                {"-02:00", "-02:00", "-02:00"},
                {"-020000", "-02:00", "-02:00"},
                {"-02:00:00", "-02:00", "-02:00"},
        };
    }



}
