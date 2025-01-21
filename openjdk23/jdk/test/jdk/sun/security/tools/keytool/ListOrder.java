/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8225392
 * @summary Comparison builds are failing due to cacerts file
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;

import java.util.Random;

public class ListOrder {

    public static void main(String[] args) throws Throwable {

        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            gen(String.format("a%02d", rand.nextInt(100)));
        }

        String last = "";
        for (String line : SecurityTools.keytool(
                "-keystore ks -storepass changeit -list").asLines()) {
            if (line.contains("PrivateKeyEntry")) {
                // This is the line starting with the alias
                System.out.println(line);
                if (line.compareTo(last) <= 0) {
                    throw new RuntimeException("Not ordered");
                } else {
                    last = line;
                }
            }
        }
    }

    static void gen(String a) throws Exception {
        // Do not check result, there might be duplicated alias(es).
        SecurityTools.keytool("-keystore ks -storepass changeit "
                + "-keyalg ec -genkeypair -alias " + a + " -dname CN=" + a);
    }
}
