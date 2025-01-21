/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.spi.MixerProvider;

import static java.util.ServiceLoader.load;

/**
 * @test
 * @key sound
 * @bug 8135100
 * @author Sergey Bylokhov
 */
public final class ExpectedNPEOnNull {

    public static void main(final String[] args) throws Exception {
        testAS();
        for (final MixerProvider mp : load(MixerProvider.class)) {
            testMP(mp);
        }
        testMP(customMP);
    }

    /**
     * Tests the part of AudioSystem API, which implemented via MixerProvider.
     */
    private static void testAS() {
        try {
            AudioSystem.getMixer(null); // null should be accepted
        } catch (final SecurityException | IllegalArgumentException ignored) {
            // skip the specified exceptions only
        }
    }

    /**
     * Tests the MixerProvider API directly.
     */
    private static void testMP(MixerProvider mp) {
        try {
            mp.isMixerSupported(null);
            throw new RuntimeException("NPE is expected: " + mp);
        } catch (final NullPointerException ignored) {

        }
        try {
            mp.getMixer(null); // null should be accepted
        } catch (SecurityException | IllegalArgumentException e) {
            // skip the specified exceptions only
        }
    }

    /**
     * Tests some default implementation of MixerProvider API, using the
     * custom {@code MixerProvider}, which support nothing.
     */
    static final MixerProvider customMP = new MixerProvider() {
        @Override
        public Mixer.Info[] getMixerInfo() {
            return new Mixer.Info[0];
        }

        @Override
        public Mixer getMixer(Mixer.Info info) {
            return null;
        }
    };
}
