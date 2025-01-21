/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.spi.SoundbankReader;

import static java.util.ServiceLoader.load;

/**
 * @test
 * @bug 8143909
 * @author Sergey Bylokhov
 */
public final class ExpectedNPEOnNull {

    public static void main(final String[] args) throws Exception {
        testMS();
        for (final SoundbankReader sbr : load(SoundbankReader.class)) {
            testSBR(sbr);
        }
    }

    /**
     * Tests the part of MidiSystem API, which implemented via SoundbankReader.
     */
    private static void testMS() throws Exception {
        // MidiSystem#getSoundbank(InputStream)
        try {
            MidiSystem.getSoundbank((InputStream) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getSoundbank(URL)
        try {
            MidiSystem.getSoundbank((URL) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getSoundbank(File)
        try {
            MidiSystem.getSoundbank((File) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
    }

    /**
     * Tests the SoundbankReader API directly.
     */
    private static void testSBR(final SoundbankReader sbr) throws Exception {
        // SoundbankReader#getSoundbank(InputStream)
        try {
            sbr.getSoundbank((InputStream) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // SoundbankReader#getSoundbank(URL)
        try {
            sbr.getSoundbank((URL) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // SoundbankReader#getSoundbank(File)
        try {
            sbr.getSoundbank((File) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
    }
}
