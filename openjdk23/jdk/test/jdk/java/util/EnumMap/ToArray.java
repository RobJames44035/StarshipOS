/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6310858
 * @summary Tests for toArray
 * @author  Martin Buchholz
 */

import java.util.*;

public class ToArray {
    enum Country { FRENCH, POLISH }
    public static void main(String[] args) throws Throwable {
        Map<Country, String> m = new EnumMap<Country, String>(Country.class);
        m.put(Country.FRENCH, "connection");
        m.put(Country.POLISH, "sausage");

        Object[] z = m.entrySet().toArray();
        System.out.println(Arrays.toString(z));
        if (! (z.getClass() == Object[].class &&
               z.length == 2 &&
               ((Map.Entry)z[0]).getKey() == Country.FRENCH &&
               ((Map.Entry)z[1]).getKey() == Country.POLISH))
            throw new AssertionError();

        Map.Entry[] x1 = new Map.Entry[3];
        x1[2] = m.entrySet().iterator().next();
        Map.Entry[] x2 = m.entrySet().toArray(x1);
        System.out.println(Arrays.toString(x2));
        if (! (x1 == x2 &&
               x2[0].getKey() == Country.FRENCH &&
               x2[1].getKey() == Country.POLISH &&
               x2[2] == null))
            throw new AssertionError();

        Map.Entry[] y1 = new Map.Entry[1];
        Map.Entry[] y2 = m.entrySet().toArray(y1);
        System.out.println(Arrays.toString(y2));
        if (! (y1 != y2 &&
               y2.length == 2 &&
               y2[0].getKey() == Country.FRENCH &&
               y2[1].getKey() == Country.POLISH))
            throw new AssertionError();
    }
}
