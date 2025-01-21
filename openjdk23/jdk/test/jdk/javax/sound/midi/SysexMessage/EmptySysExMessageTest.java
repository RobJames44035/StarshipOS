/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * @test
 * @key sound
 * @bug 8282578
 * @summary AIOOBE in javax.sound.sampled.Clip
 * @run main EmptySysExMessageTest
 */

public class EmptySysExMessageTest {
    public static void main(String[] args) {
        String sep = System.getProperty("file.separator");
        String dir = System.getProperty("test.src", ".");
        String name = "zerosysex.mid";
        try {
            readAudioFile(dir + sep + name);
        } catch (Throwable t) {
            throw new RuntimeException("Invalid file " + name
                    + " caused unexpected exception during read: "
                    + t + System.lineSeparator());
        }
    }

    static void readAudioFile(String name) throws IOException {
        File soundFile = new File(name);
        Path path = Paths.get(soundFile.getAbsolutePath());
        byte[] samples = Files.readAllBytes(path);

        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new ByteArrayInputStream(samples));
            try (Clip clip = AudioSystem.getClip()) {
                clip.open(audioInputStream);
                clip.start();
                Thread.sleep(1000);
                clip.stop();
            }
        } catch (UnsupportedAudioFileException
                 | LineUnavailableException
                 | IOException
                 | InterruptedException
                 | IllegalArgumentException
                 | IllegalStateException
                 | SecurityException expected) {
            // Do nothing, these types of exception are expected on invalid file
        }
    }
}
