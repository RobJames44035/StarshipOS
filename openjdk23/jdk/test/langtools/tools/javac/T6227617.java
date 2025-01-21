/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
import java.util.HashMap;
import java.util.Map;

class T6227617 {
    void m() {
        int i1 = 2;
        int i2 = (int) i1;  // warn

        float f1 = 1f;
        int i3 = (int) f1;

        String s = (String) ""; // warn
        Object o = (Object) "";

        Map<String, Integer> m = new HashMap<String, Integer>();
        Integer I1 = (Integer) m.get(""); // warn
    }

    // The following cause NPE in Attr with an Attr-based solution for -Xlint:cast
    static final int i1 = Foo.i1;
    static final String s = Foo.s;
}

class Foo
{
    static final int i1 = (int) 1;
    static final int i2 = (int) 1L;

    static final String s = (String) "abc";
}
