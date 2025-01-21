/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.spi.MidiFileReader;

import static java.util.ServiceLoader.load;

/**
 * @test
 * @bug 8143909
 * @author Sergey Bylokhov
 */
public final class ExpectedNPEOnNull {

    public static void main(final String[] args) throws Exception {
        testMS();
        for (final MidiFileReader mfr : load(MidiFileReader.class)) {
            testMFR(mfr);
        }
    }

    /**
     * Tests the part of MidiSystem API, which implemented via MidiFileReader.
     */
    private static void testMS() throws Exception {
        // MidiSystem#getMidiFileFormat(InputStream)
        try {
            MidiSystem.getMidiFileFormat((InputStream) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getMidiFileFormat(URL)
        try {
            MidiSystem.getMidiFileFormat((URL) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getMidiFileFormat(File)
        try {
            MidiSystem.getMidiFileFormat((File) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getSequence(InputStream)
        try {
            MidiSystem.getSequence((InputStream) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getSequence(URL)
        try {
            MidiSystem.getSequence((URL) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiSystem#getSequence(File)
        try {
            MidiSystem.getSequence((File) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
    }

    /**
     * Tests the MidiFileReader API directly.
     */
    private static void testMFR(final MidiFileReader mfr) throws Exception {
        // MidiFileReader#getMidiFileFormat(InputStream)
        try {
            mfr.getMidiFileFormat((InputStream) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiFileReader#getMidiFileFormat(URL)
        try {
            mfr.getMidiFileFormat((URL) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiFileReader#getMidiFileFormat(File)
        try {
            mfr.getMidiFileFormat((File) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiFileReader#getSequence(InputStream)
        try {
            mfr.getSequence((InputStream) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiFileReader#getSequence(URL)
        try {
            mfr.getSequence((URL) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiFileReader#getSequence(File)
        try {
            mfr.getSequence((File) null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
    }
}
