/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 6191224
 * @summary (reflect) Misleading detail string in IllegalArgumentException thrown by Array.get<Type>
 * @run main ArrayGetIntException
 */
import java.io.*;
import java.lang.reflect.Array;

public class ArrayGetIntException {
    public static void main(String[] args) throws Exception {
        Object[] objArray = {Integer.valueOf(Integer.MAX_VALUE)};

        // this access is legal
        try {
            System.out.println(Array.get(objArray, 0));
            System.out.println("Test #1 PASSES");
        } catch(Exception e) {
            failTest("Test #1 FAILS - legal access denied" + e.getMessage());
        }

        // this access is not legal, but needs to generate the proper exception message
        try {
            System.out.println(Array.getInt(objArray, 0));
            failTest("Test #2 FAILS - no exception");
        } catch(Exception e) {
            System.out.println(e);
            if (e.getMessage().equals("Argument is not an array of primitive type")) {
                System.out.println("Test #2 PASSES");
            } else {
                failTest("Test #2 FAILS - incorrect message: " + e.getMessage());
            }
        }

        // this access is not legal, but needs to generate the proper exception message
        try {
            System.out.println(Array.getInt(new Object(), 0));
            failTest("Test #3 FAILS - no exception");
        } catch(Exception e) {
            System.out.println(e);
            if (e.getMessage().equals("Argument is not an array")) {
                System.out.println("Test #3 PASSES");
            } else {
                failTest("Test #3 FAILS - incorrect message: " + e.getMessage());
            }
        }
    }

    private static void failTest(String errStr) {
        System.out.println(errStr);
        throw new Error(errStr);
    }
}
