/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug     7164256
 * @summary EnumMap.entrySet() returns an entrySet referencing to the cloned instance
 * @author  Diego Belfer
 */

import java.util.EnumMap;

public class ProperEntrySetOnClone {
    public enum Test {
        ONE, TWO
    }

    public static void main(String[] args) {
        EnumMap<Test, String> map1 = new EnumMap<Test, String>(Test.class);
        map1.put(Test.ONE, "1");
        map1.put(Test.TWO, "2");

        // We need to force creation of the map1.entrySet
        int size = map1.entrySet().size();
        if (size != 2) {
            throw new RuntimeException(
                    "Invalid size in original map. Expected: 2 was: " + size);
        }

        EnumMap<Test, String> map2 = map1.clone();
        map2.remove(Test.ONE);
        size = map2.entrySet().size();
        if (size != 1) {
            throw new RuntimeException(
                    "Invalid size in cloned instance. Expected: 1 was: " + size);
        }
    }
}
