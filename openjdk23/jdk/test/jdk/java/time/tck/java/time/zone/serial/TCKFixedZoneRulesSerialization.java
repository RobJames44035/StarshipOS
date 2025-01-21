/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.zone.serial;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;

import static org.testng.Assert.assertEquals;

/**
 * Test serialization of ZoneRules for fixed offset time-zones.
 */
@Test
public class TCKFixedZoneRulesSerialization {

    private static final ZoneOffset OFFSET_PONE = ZoneOffset.ofHours(1);
    private static final ZoneOffset OFFSET_PTWO = ZoneOffset.ofHours(2);
    private static final ZoneOffset OFFSET_M18 = ZoneOffset.ofHours(-18);

    private ZoneRules make(ZoneOffset offset) {
        return offset.getRules();
    }

    @DataProvider(name="rules")
    Object[][] data_rules() {
        return new Object[][] {
            {make(OFFSET_PONE), OFFSET_PONE},
            {make(OFFSET_PTWO), OFFSET_PTWO},
            {make(OFFSET_M18), OFFSET_M18},
        };
    }

    //-----------------------------------------------------------------------
    // Basics
    //-----------------------------------------------------------------------
    @Test(dataProvider="rules")
    public void test_serialization(ZoneRules test, ZoneOffset expectedOffset) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(test);
        baos.close();
        byte[] bytes = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bais);
        ZoneRules result = (ZoneRules) in.readObject();

        assertEquals(result, test);
        assertEquals(result.getClass(), test.getClass());
    }


}
