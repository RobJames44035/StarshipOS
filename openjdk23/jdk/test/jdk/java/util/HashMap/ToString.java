/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4189821
 * @summary HashMap's entry.toString threw a null pointer exc if the HashMap
 *          contained null keys or values.
 */

import java.util.HashMap;
import java.util.Map;

public class ToString {
    public static void main(String[] args) throws Exception {
        Map m = new HashMap();
        m.put(null, null);
        m.entrySet().iterator().next().toString();
    }
}
