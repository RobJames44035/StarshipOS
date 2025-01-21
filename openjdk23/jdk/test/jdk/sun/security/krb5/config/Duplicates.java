/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
/*
 * @test
 * @bug 7184246
 * @modules java.security.jgss/sun.security.krb5
 * @compile -XDignore.symbol.file Duplicates.java
 * @run main/othervm Duplicates
 * @summary Simplify Config.get() of krb5
 */

import sun.security.krb5.Config;

public class Duplicates {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.krb5.conf",
                System.getProperty("test.src", ".") +"/k1.conf");
        Config config = Config.getInstance();
        config.listTable();
        String s;

        // root section merged
        s = config.get("libdefaults", "default_realm");
        if (!s.equals("R1")) {
            throw new Exception();
        }
        // Former is preferred to latter for strings and sections
        s = config.get("libdefaults", "default_tkt_enctypes");
        if (!s.equals("aes128-cts")) {
            throw new Exception();
        }
        s = config.get("realms", "R1", "kdc");
        if (!s.equals("k1")) {
            throw new Exception(s);
        }
        // Duplicate keys in [realms] are merged, and sections with the same
        // name in between ignored
        s = config.getAll("realms", "R2", "kdc");
        if (!s.equals("k1 k2 k3 k4")) {
            throw new Exception(s);
        }
        // Duplicate keys in [capaths] are merged
        s = config.getAll("capaths", "R1", "R2");
        if (!s.equals("R3 R4 R5 R6")) {
            throw new Exception(s);
        }
        // We can be very deep now
        s = config.get("new", "x", "y", "z", "a", "b", "c");
        if (!s.equals("d")) {
            throw new Exception(s);
        }
    }
}
