/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.File;
import java.io.OutputStream;
import java.util.Objects;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.spi.MidiFileWriter;

import static java.util.ServiceLoader.load;

/**
 * @test
 * @bug 8143909
 * @author Sergey Bylokhov
 */
public final class ExpectedNPEOnNull {

    public static void main(final String[] args) throws Exception {
        testMS();
        for (final MidiFileWriter mfw : load(MidiFileWriter.class)) {
            testMFW(mfw);
        }
        testMFW(customMFW);
    }

    /**
     * Tests the part of MidiSystem API, which implemented via MidiFileWriter.
     */
    private static void testMS() throws Exception {
        // MidiSystem#getMidiFileTypes(Sequence)
        try {
            MidiSystem.getMidiFileTypes(null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }

        // MidiSystem#isFileTypeSupported(int, Sequence)
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.isFileTypeSupported(type, null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        // MidiSystem#write(Sequence, int, OutputStream)
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.write(null, type, new NullOutputStream());
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.write(new Sequence(0, 0), type, (OutputStream) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.write(null, type, (OutputStream) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        // MidiSystem#write(Sequence, int, File)
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.write(null, type, new File(""));
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.write(new Sequence(0, 0), type, (File) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                MidiSystem.write(null, type, (File) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
    }

    /**
     * Tests the MidiFileWriter API directly.
     */
    private static void testMFW(final MidiFileWriter mfw) throws Exception {
        // MidiFileWriter#getMidiFileTypes(Sequence)
        try {
            mfw.getMidiFileTypes(null);
            throw new RuntimeException("NPE is expected");
        } catch (final NullPointerException ignored) {
        }
        // MidiFileWriter#isFileTypeSupported(int, Sequence)
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.isFileTypeSupported(type, null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        // MidiFileWriter#write(Sequence, int, OutputStream)
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.write(null, type, new NullOutputStream());
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.write(new Sequence(0, 0), type, (OutputStream) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.write(null, type, (OutputStream) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        // MidiFileWriter#write(Sequence, int, File)
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.write(null, type, new File(""));
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.write(new Sequence(0, 0), type, (File) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
        for (final int type : MidiSystem.getMidiFileTypes()) {
            try {
                mfw.write(null, type, (File) null);
                throw new RuntimeException("NPE is expected");
            } catch (final NullPointerException ignored) {
            }
        }
    }
    /**
     * Tests some default implementation of MidiFileWriter API, using the custom
     * {@code MidiFileWriter}, which support nothing.
     */
    static MidiFileWriter customMFW = new MidiFileWriter() {
        @Override
        public int[] getMidiFileTypes() {
            return new int[0];
        }

        @Override
        public int[] getMidiFileTypes(Sequence sequence) {
            Objects.requireNonNull(sequence);
            return new int[0];
        }

        @Override
        public int write(Sequence in, int fileType, OutputStream out) {
            Objects.requireNonNull(in);
            Objects.requireNonNull(out);
            return 0;
        }

        @Override
        public int write(Sequence in, int fileType, File out) {
            Objects.requireNonNull(in);
            Objects.requireNonNull(out);
            return 0;
        }
    };

    private static final class NullOutputStream extends OutputStream {

        @Override
        public void write(final int b) {
            //do nothing
        }
    }
}
