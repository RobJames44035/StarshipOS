/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4918769 8130181
 * @summary make sure Provider.equals() behaves as expected with the id attributes
 * @author Andreas Sterbenz
 */

import java.security.*;

public class Equals {

    public static void main(String[] args) throws Exception {
        Provider p1 = new P1("foo", "1.0", "foo");
        Provider p1b = new P1("foo", "1.0", "foo");
        Provider p2 = new P2("foo", "1.0", "foo");
        System.out.println(p1.entrySet());
        if (p1.equals(p2)) {
            throw new Exception("Objects are equal");
        }
        if (p1.equals(p1b) == false) {
            throw new Exception("Objects not equal");
        }
        p1.clear();
        if (p1.equals(p1b) == false) {
            throw new Exception("Objects not equal");
        }
        p1.put("Provider.id name", "bar");
        p1.remove("Provider.id version");
        if (p1.equals(p1b) == false) {
            throw new Exception("Objects not equal");
        }
    }

    private static class P1 extends Provider {
        P1(String name, String version, String info) {
            super(name, version, info);
        }
    }

    private static class P2 extends Provider {
        P2(String name, String version, String info) {
            super(name, version, info);
        }
    }

}
