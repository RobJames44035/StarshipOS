/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.NOT_SPECIFIED;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/*
 * @test
 * @key headful
 * @bug 8279673
 * @summary Verify no NPE creating threads
 * @run main/othervm DataPusherThreadCheck
 */
public class DataPusherThreadCheck {

    public static void main(String[] args) throws Exception {
        // Prepare the audio file
        File file = new File("audio.wav");
        try {
            AudioFormat format =
                    new AudioFormat(PCM_SIGNED, 44100, 8, 1, 1, 44100, false);
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            if (!(AudioSystem.isLineSupported(info)) ) {
                return; // the test is not applicable
            }
            int dataSize = 6000*1000 * format.getFrameSize();
            InputStream in = new ByteArrayInputStream(new byte[dataSize]);
            AudioInputStream audioStream = new AudioInputStream(in, format, NOT_SPECIFIED);
            AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, file);
        } catch (Exception ignored) {
            return; // the test is not applicable
        }
        try {
            checkThread(file);
        } finally {
            Files.delete(file.toPath());
        }
    }

    private static void checkThread(File file) throws Exception {
        AudioClip clip = (AudioClip) file.toURL().getContent();
        clip.loop();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        boolean found = isDataPushedThreadExist();
        clip.stop();
        if (!found) {
            throw new RuntimeException("Thread 'DataPusher' isn't found");
        }
    }

    private static boolean isDataPushedThreadExist() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("DataPusher")) {
                return true;
            }
        }
        return false;
    }

}
