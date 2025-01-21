/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8302976
 * @summary Verify conversion between float and the binary16 format
 * @requires (vm.cpu.features ~= ".*avx512vl.*" | vm.cpu.features ~= ".*f16c.*") | os.arch == "aarch64"
 *           | (os.arch == "riscv64" & vm.cpu.features ~= ".*zfh.*")
 *           | ((os.arch == "ppc64" | os.arch == "ppc64le") & vm.cpu.features ~= ".*darn.*")
 * @requires vm.compiler1.enabled & vm.compiler2.enabled
 * @requires vm.compMode != "Xcomp"
 * @comment default run:
 * @run main TestAllFloat16ToFloat
 * @comment disable intrinsics:
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:DisableIntrinsic=_float16ToFloat,_floatToFloat16 TestAllFloat16ToFloat
 * @comment eager JIT compilation:
 * @run main/othervm -XX:CompileCommand=compileonly,TestAllFloat16ToFloat::test* -Xbatch TestAllFloat16ToFloat
 * @comment C2 JIT compilation only:
 * @run main/othervm -XX:CompileCommand=compileonly,TestAllFloat16ToFloat::test* -Xbatch -XX:-TieredCompilation TestAllFloat16ToFloat
 * @comment C1 JIT compilation only:
 * @run main/othervm -XX:CompileCommand=compileonly,TestAllFloat16ToFloat::test* -Xbatch -XX:TieredStopAtLevel=1 TestAllFloat16ToFloat
 */

public class TestAllFloat16ToFloat {
    public static short testFloatToFloat16(float f) {
        return Float.floatToFloat16(f);
    }

    public static float testFloat16ToFloat(short s) {
        return Float.float16ToFloat(s);
    }

    public static short testRoundTrip(short s) {
        return Float.floatToFloat16(Float.float16ToFloat(s));
    }

    public static int verify(short sVal, float fVal, short sRes, String prefix) {
        int errors = 0;
        if (sRes != sVal) {
            if (!Float.isNaN(fVal) || ((sRes & ~0x0200) != (sVal & ~0x0200)) ) {
                errors++;
                String fVal_hex = Integer.toHexString(Float.floatToRawIntBits(fVal));
                String sRes_hex = Integer.toHexString(sRes & 0xffff);
                String sVal_hex = Integer.toHexString(sVal & 0xffff);
                System.out.println(prefix + "Inconsistent result for Float.floatToFloat16(" + fVal + "/" + fVal_hex + "): " +
                                   sRes_hex + "(" + sRes + ")" + " != " + sVal_hex + "(" + sVal + ")");
           }
        }
        return errors;
    }

    public static int run() {
        int errors = 0;
        // Testing all float16 values.
        for (short sVal = Short.MIN_VALUE; sVal < Short.MAX_VALUE; ++sVal) {
            float fVal = Float.float16ToFloat(sVal);
            short sRes = testFloatToFloat16(fVal);
            errors += verify(sVal, fVal, sRes, "testFloatToFloat16: ");
            float fRes = testFloat16ToFloat(sVal);
            if (!Float.isNaN(fRes) && fRes != fVal) {
                errors++;
                String sVal_hex = Integer.toHexString(sVal & 0xffff);
                String fRes_hex = Integer.toHexString(Float.floatToRawIntBits(fRes));
                String fVal_hex = Integer.toHexString(Float.floatToRawIntBits(fVal));
                System.out.println("Non-NaN res: " + "Inconsistent result for Float.float16ToFloat(" + sVal_hex + "): " +
                                   fRes + "/" + fRes_hex + " != " + fVal + "/" + fVal_hex);
            }
            sRes = testRoundTrip(sVal);
            errors += verify(sVal, fVal, sRes, "testRoundTrip: ");
            if (Float.floatToFloat16(fRes) != Float.floatToFloat16(fVal)) {
                errors++;
                String sVal_hex = Integer.toHexString(sVal & 0xffff);
                String sfRes_hex = Integer.toHexString(Float.floatToFloat16(fRes) & 0xffff);
                String sfVal_hex = Integer.toHexString(Float.floatToFloat16(fVal) & 0xffff);
                System.out.println("Float16 not equal: " + "Inconsistent result for Float.float16ToFloat(" + sVal_hex + "): " +
                                   sfRes_hex + " != " + sfVal_hex);
            }
        }
        return errors;
    }

    public static void main(String[] args) {
        int errors = 0;
        // Run twice to trigger compilation
        for (int i = 0; i < 2; i++) {
            errors += run();
        }
        if (errors > 0) {
            throw new RuntimeException(errors + " errors");
        }
    }
}
