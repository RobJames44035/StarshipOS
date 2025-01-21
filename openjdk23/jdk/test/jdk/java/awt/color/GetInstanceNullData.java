/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.color.ICC_Profile;
import java.awt.color.ICC_ProfileGray;
import java.awt.color.ICC_ProfileRGB;

/**
 * @test
 * @bug 4176618 7042594 6211198
 * @summary This interactive test verifies that passing null to
 *          ICC_ProfileRGB.getInstance() does not crash the VM.
 *          An IllegalArgumentException: Invalid ICC Profile Data should be
 *          generated.
 */
public final class GetInstanceNullData {

    public static void main(String[] argv) {
        byte b[] = null;
        try {
            ICC_ProfileRGB p = (ICC_ProfileRGB) ICC_ProfileRGB.getInstance(b);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignored) {
            // expected
        }
        try {
            ICC_ProfileGray p = (ICC_ProfileGray) ICC_ProfileGray.getInstance(b);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignored) {
            // expected
        }
        try {
            ICC_Profile p = ICC_Profile.getInstance(b);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignored) {
            // expected
        }
    }
}
