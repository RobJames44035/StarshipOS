/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8068254
 * @summary Method reference uses wrong qualifying type
 * @author srikanth
 * @run main MethodReferencePackagePrivateQualifier
 */
import pkg.B;
public class MethodReferencePackagePrivateQualifier {
    public static void main(String... args) {
        pkg.B.m();
        Runnable r = pkg.B::m;
        r.run();
        r = B::m;
        r.run();
        if (!pkg.B.result.equals("A.m()A.m()A.m()"))
            throw new AssertionError("Incorrect result");
    }
}
