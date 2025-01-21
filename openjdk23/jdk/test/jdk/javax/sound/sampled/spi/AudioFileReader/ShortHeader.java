/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileReader;

import static java.util.ServiceLoader.load;

/**
 * @test
 * @bug 8131974
 * @summary Short files should be reported as unsupported
 */
public final class ShortHeader {

    private static byte[] W = {-12, 3, 45};

    private static byte[] R = new byte[3];

    public static void main(final String[] args) throws Exception {
        final File file = Files.createTempFile("audio", "test").toFile();
        try {
            try (final OutputStream fos = new FileOutputStream(file)) {
                fos.write(W);
            }
            testAS(file);
            for (final AudioFileReader afr : load(AudioFileReader.class)) {
                testAFR(afr, file);
            }
        } finally {
            Files.delete(Paths.get(file.getAbsolutePath()));
        }
    }

    /**
     * Tests the part of AudioSystem API, which implemented via
     * AudioFileReader.
     *
     * @see AudioSystem#getAudioFileFormat(InputStream)
     * @see AudioSystem#getAudioFileFormat(File)
     * @see AudioSystem#getAudioFileFormat(URL)
     * @see AudioSystem#getAudioInputStream(InputStream)
     * @see AudioSystem#getAudioInputStream(File)
     * @see AudioSystem#getAudioInputStream(URL)
     */
    private static void testAS(final File file) throws IOException {
        try {
            AudioSystem.getAudioFileFormat(file);
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }
        try {
            AudioSystem.getAudioFileFormat(file.toURL());
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }
        try {
            AudioSystem.getAudioInputStream(file);
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }
        try {
            AudioSystem.getAudioInputStream(file.toURL());
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }

        //  AudioSystem.getAudioXXX(stream) should properly reset the stream

        try (FileInputStream fis = new FileInputStream(file);
             InputStream stream = new BufferedInputStream(fis)) {

            try {
                AudioSystem.getAudioFileFormat(stream);
                throw new RuntimeException();
            } catch (final UnsupportedAudioFileException ignored) {
            }
            try {
                AudioSystem.getAudioInputStream(stream);
                throw new RuntimeException();
            } catch (final UnsupportedAudioFileException ignored) {
            }
            stream.read(R, 0, R.length);
        }

        if (!Arrays.equals(R, W)) {
            System.err.println("Expected = " + Arrays.toString(W));
            System.err.println("Actual = " + Arrays.toString(R));
            throw new RuntimeException();
        }
    }
    /**
     * Tests the AudioFileReader API directly.
     *
     * @see AudioFileReader#getAudioFileFormat(InputStream)
     * @see AudioFileReader#getAudioFileFormat(File)
     * @see AudioFileReader#getAudioFileFormat(URL)
     * @see AudioFileReader#getAudioInputStream(InputStream)
     * @see AudioFileReader#getAudioInputStream(File)
     * @see AudioFileReader#getAudioInputStream(URL)
     */
    private static void testAFR(final AudioFileReader fcp, final File file)
            throws Exception {
        try {
            fcp.getAudioFileFormat(file);
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }
        try {
            fcp.getAudioFileFormat(file.toURL());
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }
        try {
            fcp.getAudioInputStream(file);
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }
        try {
            fcp.getAudioInputStream(file.toURL());
            throw new RuntimeException();
        } catch (final UnsupportedAudioFileException ignored) {
        }

        // AudioFileReader should properly reset the stream

        try (FileInputStream fis = new FileInputStream(file);
             InputStream stream = new BufferedInputStream(fis)) {

            try {
                fcp.getAudioFileFormat(stream);
                throw new RuntimeException();
            } catch (final UnsupportedAudioFileException ignored) {
            }
            try {
                fcp.getAudioInputStream(stream);
                throw new RuntimeException();
            } catch (final UnsupportedAudioFileException ignored) {
            }
            stream.read(R, 0, R.length);
        }

        if (!Arrays.equals(R, W)) {
            System.err.println("Expected = " + Arrays.toString(W));
            System.err.println("Actual = " + Arrays.toString(R));
            throw new RuntimeException();
        }
    }
}
