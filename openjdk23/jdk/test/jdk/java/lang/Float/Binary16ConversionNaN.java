/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8289551
 * @requires (os.arch != "x86" & os.arch != "i386") | vm.opt.UseSSE == "null" | vm.opt.UseSSE > 0
 * @summary Verify NaN sign and significand bits are preserved across conversions
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions
 * -XX:DisableIntrinsic=_float16ToFloat,_floatToFloat16 Binary16ConversionNaN
 */

/*
 * The behavior tested below is an implementation property not
 * required by the specification. It would be acceptable for this
 * information to not be preserved (as long as a NaN is returned) if,
 * say, a intrinsified version using native hardware instructions
 * behaved differently.
 *
 * If that is the case, this test should be modified to disable
 * intrinsics or to otherwise not run on platforms with an differently
 * behaving intrinsic.
 */
public class Binary16ConversionNaN {
    public static void main(String... argv) {
        int errors = 0;
        errors += binary16NaNRoundTrip();

        if (errors > 0)
            throw new RuntimeException(errors + " errors");
    }

    /*
     * Put all 16-bit NaN values through a conversion loop and make
     * sure the significand, sign, and exponent are all preserved.
     */
    private static int binary16NaNRoundTrip() {
        int errors = 0;
        final int NAN_EXPONENT = 0x7c00;
        final int SIGN_BIT     = 0x8000;

        // A NaN has a nonzero significand
        for (int i = 1; i <= 0x3ff; i++) {
            short binary16NaN = (short)(NAN_EXPONENT | i);
            assert isNaN(binary16NaN);
            errors += testRoundTrip(                   binary16NaN);
            errors += testRoundTrip((short)(SIGN_BIT | binary16NaN));
        }
        return errors;
    }

    private static boolean isNaN(short binary16) {
        return ((binary16 & 0x7c00) == 0x7c00) // Max exponent and...
            && ((binary16 & 0x03ff) != 0 );    // significand nonzero.
    }

    private static int testRoundTrip(int i) {
        int errors = 0;
        short s = (short)i;
        float f =  Float.float16ToFloat(s);
        short s2 = Float.floatToFloat16(f);

        if (s != s2) {
            errors++;
            System.out.println("Roundtrip failure on NaN value " +
                               Integer.toHexString(0xFFFF & (int)s) +
                               "\t got back " + Integer.toHexString(0xFFFF & (int)s2));
        }
        return errors;
    }
}
