/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.color.ColorSpace;
import java.awt.color.ICC_Profile;
import java.util.Arrays;

/**
 * @test
 * @bug 8321489
 * @summary tests that the cmm id is not ignored
 */
public final class CustomCMMID {

    private static final byte[] JAVA_ID = {
            (byte) 'j', (byte) 'a', (byte) 'v', (byte) 'a',
    };

    private static final int[] CS = {
            ColorSpace.CS_CIEXYZ, ColorSpace.CS_GRAY, ColorSpace.CS_LINEAR_RGB,
            ColorSpace.CS_PYCC, ColorSpace.CS_sRGB
    };

    public static void main(String[] args) {
        for (int cs : CS) {
            ICC_Profile p = createProfile(cs);
            validate(p);
        }
    }

    private static ICC_Profile createProfile(int type) {
        byte[] data = ICC_Profile.getInstance(type).getData();
        System.arraycopy(JAVA_ID, 0, data, ICC_Profile.icHdrCmmId,
                         JAVA_ID.length);
        return ICC_Profile.getInstance(data);
    }

    private static void validate(ICC_Profile p) {
        byte[] header = p.getData(ICC_Profile.icSigHead);
        byte[] id = new byte[JAVA_ID.length];
        System.arraycopy(header, ICC_Profile.icHdrCmmId, id, 0, JAVA_ID.length);

        if (!java.util.Arrays.equals(id, JAVA_ID)) {
            System.err.println("Expected: " + Arrays.toString(JAVA_ID));
            System.err.println("Actual: " + Arrays.toString(id));
            throw new RuntimeException("Wrong cmm id");
        }
    }
}
