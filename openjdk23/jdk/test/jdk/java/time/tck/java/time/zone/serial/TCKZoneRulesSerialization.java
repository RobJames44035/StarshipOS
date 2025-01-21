/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.zone.serial;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.ZoneId;
import java.time.zone.ZoneRules;

import static org.testng.Assert.assertEquals;

import tck.java.time.AbstractTCKTest;

/**
 * Test serialization of ZoneRules.
 */
@Test
public class TCKZoneRulesSerialization extends AbstractTCKTest{

    public void test_serialization_loaded() throws Exception {
        assertSerialization(europeLondon());
        assertSerialization(europeParis());
        assertSerialization(americaNewYork());
    }

    private void assertSerialization(ZoneRules test) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(test);
        baos.close();
        byte[] bytes = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bais);
        ZoneRules result = (ZoneRules) in.readObject();

        assertEquals(result, test);
    }

    //-----------------------------------------------------------------------
    // Europe/London
    //-----------------------------------------------------------------------
    private ZoneRules europeLondon() {
        return ZoneId.of("Europe/London").getRules();
    }


    //-----------------------------------------------------------------------
    // Europe/Paris
    //-----------------------------------------------------------------------
    private ZoneRules europeParis() {
        return ZoneId.of("Europe/Paris").getRules();
    }

    //-----------------------------------------------------------------------
    // America/New_York
    //-----------------------------------------------------------------------
    private ZoneRules americaNewYork() {
        return ZoneId.of("America/New_York").getRules();
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(ZoneRules.class);
    }

}
