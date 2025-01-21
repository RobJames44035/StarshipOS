/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.util.Map;
import java.util.WeakHashMap;

/*
 * @test
 * @bug     4503146
 * @summary Zero initial capacity should be legal
 * @author  Josh Bloch
 */

public class ZeroInitCap {
    public static void main(String[] args) {
        Map map = new WeakHashMap(0);
        map.put("a","b");
    }
}
