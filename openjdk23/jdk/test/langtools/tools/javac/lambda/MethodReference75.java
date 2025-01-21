/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8143647
 * @summary Javac compiles method reference that allows results in an IllegalAccessError
 * @run main MethodReference75
 */

import pkg.PublicDerived8143647;

public class MethodReference75 {
    public static void main(String[] args) {
        if (java.util.Arrays
                .asList(new PublicDerived8143647())
                .stream()
                .map(PublicDerived8143647::getX)
                .findFirst()
                .get()
                .equals("PackagePrivateBase"))
            System.out.println("OK");
        else
            throw new AssertionError("Unexpected output");
    }
}
