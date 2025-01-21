/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6944033
 * @summary Tests that PCM_FLOAT encoding is supported
 * @compile PCM_FLOAT_support.java
 * @run main PCM_FLOAT_support
 * @author Alex Menkov
 *
 */

import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;


public class PCM_FLOAT_support {

    static Encoding pcmFloatEnc;

    static boolean testFailed = false;

    public static void main(String[] args) throws Exception {
        // 1st checks Encoding.PCM_FLOAT is available
        pcmFloatEnc = Encoding.PCM_FLOAT;

        Encoding[] encodings = AudioSystem.getTargetEncodings(pcmFloatEnc);
        out("conversion from PCM_FLOAT to " + encodings.length + " encodings:");
        for (Encoding e: encodings) {
            out("  - " + e);
        }
        if (encodings.length == 0) {
            testFailed = true;
        }

        test(Encoding.PCM_SIGNED);
        test(Encoding.PCM_UNSIGNED);

        if (testFailed) {
            throw new Exception("test failed");
        }
        out("test passed.");
    }

    static void out(String s) {
        System.out.println(s);
    }

    static boolean test(Encoding enc) {
        out("conversion " + enc + " -> PCM_FLOAT:");
        Encoding[] encodings = AudioSystem.getTargetEncodings(enc);
        for (Encoding e: encodings) {
            if (e.equals(pcmFloatEnc)) {
                out("  - OK");
                return true;
            }
        }
        out("  - FAILED (not supported)");
        testFailed = true;
        return false;
    }

}
