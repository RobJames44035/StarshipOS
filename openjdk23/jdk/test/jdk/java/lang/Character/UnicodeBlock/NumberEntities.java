/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8080535 8191410 8215194 8221431 8239383 8268081 8283465 8284856
 * @summary Check if the NUM_ENTITIES field reflects the correct number
 *      of Character.UnicodeBlock constants. Also checks the size of
 *      Character.UnicodeScript's "aliases" map.
 * @modules java.base/java.lang:open
 * @run testng NumberEntities
 */

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Map;

@Test
public class NumberEntities {
    public void test_UnicodeBlock_NumberEntities() throws Throwable {
        // The number of entries in Character.UnicodeBlock.map.
        // See src/java.base/share/classes/java/lang/Character.java
        Field n = Character.UnicodeBlock.class.getDeclaredField("NUM_ENTITIES");
        Field m = Character.UnicodeBlock.class.getDeclaredField("map");
        n.setAccessible(true);
        m.setAccessible(true);
        assertEquals(((Map)m.get(null)).size(), n.getInt(null));
    }
    public void test_UnicodeScript_aliases() throws Throwable {
        // The number of entries in Character.UnicodeScript.aliases.
        // See src/java.base/share/classes/java/lang/Character.java
        Field aliases = Character.UnicodeScript.class.getDeclaredField("aliases");
        aliases.setAccessible(true);
        assertEquals(((Map)aliases.get(null)).size(), Character.UnicodeScript.UNKNOWN.ordinal() + 1);
    }
}
