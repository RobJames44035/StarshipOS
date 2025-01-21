/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

/**
 * @test
 * @bug 8312535
 * @summary Check if MidiSystem.getSoundbank() throws
 *          InvalidMidiDataException when provided with invalid soundbank data
 * @run main EmptySoundBankTest
 */
public final class EmptySoundBankTest {

    public static void main(String[] args) throws Exception {
        File tempFile = new File("sound.bank");
        tempFile.createNewFile();
        try {
            MidiSystem.getSoundbank(tempFile);
            throw new RuntimeException("InvalidMidiDataException is expected");
        } catch (InvalidMidiDataException ignore) {
        } finally {
            Files.delete(Paths.get(tempFile.getAbsolutePath()));
        }
    }
}
