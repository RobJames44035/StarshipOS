/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @bug 6227536
 * @summary Verify HmacSHA1 and HmacMD5 KeyGenerators throw an Exception when
 *  a keysize of zero is requested
 */

import jdk.test.lib.Utils;

import javax.crypto.KeyGenerator;

public class Test6227536 {

    String[] keyGensToTest = new String[]{"HmacSHA1", "HmacMD5"};

    public boolean execute(String algo) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(algo,
                                System.getProperty("test.provider.name", "SunJCE"));

        Utils.runAndCheckException(() -> kg.init(0),
                IllegalArgumentException.class);

        return true;
    }

    public static void main(String[] args) throws Exception {
        Test6227536 test = new Test6227536();
        String testName = test.getClass().getName();

        for (String keyGenToTest : test.keyGensToTest) {

            if (test.execute(keyGenToTest)) {

                System.out.println(testName + ": " + keyGenToTest + " Passed!");

            }

        }

    }
}
