/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

import java.util.List;
import java.util.Map;

public final class Pattern2 {

    static void troublesCausingMethod() {
        String s = null;
        Object o;
        Map<String, Object> map = null;
        List<String> list = null;
        if (s == null) return;
        for (int i = 0; i < 10; i++) {
            String key = "";
            if (map.containsKey(key)) {
                o = map.get(key);
            } else {
                o = new Object();
                map.put(key, o);
            }
            Object bais = new Object();
            try {
                list.add(o.toString());
            } catch (Exception ce) {
                throw ce;
            }
            bais.toString();
        }
        if (list != null) {}
    }
}
