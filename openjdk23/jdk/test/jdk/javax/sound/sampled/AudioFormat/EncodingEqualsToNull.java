/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import javax.sound.sampled.AudioFormat;

/**
 * @test
 * @bug 8168998
 */
public final class EncodingEqualsToNull {

    public static void main(final String[] args) {
        final AudioFormat.Encoding enc;
        try {
            enc = new AudioFormat.Encoding(null);
        } catch (final Exception ignored) {
            // behaviour of null is not specified so ignore possible exceptions
            return;
        }
        final Object stub = new Object() {
            @Override
            public String toString() {
                return null;
            }
        };
        if (stub.equals(enc) || enc.equals(stub)) {
            throw new RuntimeException("Should not be equal");
        }
    }
}
