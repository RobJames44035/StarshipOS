/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @bug 8051408 8158534
 * @summary Make sure DrbgParameters coded as specified
 * @library /test/lib
 * @build jdk.test.lib.Asserts
 * @run main DrbgParametersSpec
 */

import jdk.test.lib.Asserts;

import java.security.DrbgParameters;
import java.util.Arrays;

import static java.security.DrbgParameters.Capability.*;

public class DrbgParametersSpec {

    public static void main(String args[]) throws Exception {

        byte[] p, np1, np2;

        // Capability
        Asserts.assertTrue(PR_AND_RESEED.supportsPredictionResistance());
        Asserts.assertTrue(PR_AND_RESEED.supportsReseeding());
        Asserts.assertFalse(RESEED_ONLY.supportsPredictionResistance());
        Asserts.assertTrue(RESEED_ONLY.supportsReseeding());
        Asserts.assertFalse(NONE.supportsPredictionResistance());
        Asserts.assertFalse(NONE.supportsReseeding());

        // Instantiation
        p = "Instantiation".getBytes();
        DrbgParameters.Instantiation ins = DrbgParameters
                .instantiation(192, RESEED_ONLY, p);
        Asserts.assertTrue(ins.getStrength() == 192);
        Asserts.assertTrue(ins.getCapability() == RESEED_ONLY);
        np1 = ins.getPersonalizationString();
        np2 = ins.getPersonalizationString();
        // Getter outputs have same content but not the same object
        Asserts.assertTrue(Arrays.equals(np1, p));
        Asserts.assertTrue(Arrays.equals(np2, p));
        Asserts.assertNE(np1, np2);
        // Changes to original input has no affect on object
        p[0] = 'X';
        np2 = ins.getPersonalizationString();
        Asserts.assertTrue(Arrays.equals(np1, np2));

        ins = DrbgParameters.instantiation(-1, NONE, null);
        Asserts.assertNull(ins.getPersonalizationString());

        iae(() -> DrbgParameters.instantiation(-2, NONE, null));
        npe(() -> DrbgParameters.instantiation(-1, null, null));

        // NextBytes
        p = "NextBytes".getBytes();
        DrbgParameters.NextBytes nb = DrbgParameters
                .nextBytes(192, true, p);
        Asserts.assertTrue(nb.getStrength() == 192);
        Asserts.assertTrue(nb.getPredictionResistance());
        np1 = nb.getAdditionalInput();
        np2 = nb.getAdditionalInput();
        // Getter outputs have same content but not the same object
        Asserts.assertTrue(Arrays.equals(np1, p));
        Asserts.assertTrue(Arrays.equals(np2, p));
        Asserts.assertNE(np1, np2);
        // Changes to original input has no affect on object
        p[0] = 'X';
        np2 = nb.getAdditionalInput();
        Asserts.assertTrue(Arrays.equals(np1, np2));

        iae(() -> DrbgParameters.nextBytes(-2, false, null));

        // Reseed
        p = "Reseed".getBytes();
        DrbgParameters.Reseed rs = DrbgParameters
                .reseed(true, p);
        Asserts.assertTrue(rs.getPredictionResistance());
        np1 = rs.getAdditionalInput();
        np2 = rs.getAdditionalInput();
        // Getter outputs have same content but not the same object
        Asserts.assertTrue(Arrays.equals(np1, p));
        Asserts.assertTrue(Arrays.equals(np2, p));
        Asserts.assertNE(np1, np2);
        // Changes to original input has no affect on object
        p[0] = 'X';
        np2 = rs.getAdditionalInput();
        Asserts.assertTrue(Arrays.equals(np1, np2));
    }

    static void iae(RunnableWithException r) throws Exception {
        checkException(r, IllegalArgumentException.class);
    }

    static void npe(RunnableWithException r) throws Exception {
        checkException(r, NullPointerException.class);
    }

    interface RunnableWithException {
        void run() throws Exception;
    }

    static void checkException(RunnableWithException r, Class ex)
            throws Exception {
        try {
            r.run();
        } catch (Exception e) {
            if (ex.isAssignableFrom(e.getClass())) {
                return;
            }
            throw e;
        }
        throw new Exception("No exception thrown");
    }
}
