/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * @test
 * @bug 8266421 8269091
 * @summary Tests that Clip.setFramePosition/setMicrosecondPosition do not hang.
 */
public final class SetPositionHang implements Runnable {

    private static volatile boolean testFramePosition;
    private final Clip clip;
    private final String thread;

    private SetPositionHang(String thread, Clip clip) {
        this.thread = thread;
        this.clip = clip;
    }

    public static void main(String[] args) throws Exception {
        testFramePosition = false;
        test();
        testFramePosition = true;
        test();
    }

    private static void test() throws InterruptedException {
        try (Clip clip = AudioSystem.getClip()) {
            // prepare audio data
            int frameCount = 441000; // lets say 10 seconds
            AudioFormat format = new AudioFormat(44100.0f, 16, 2, true, false);
            byte[] bytes = new byte[frameCount * format.getFrameSize()];

            clip.open(format, bytes, 0, frameCount);
            Thread t1 = new Thread(new SetPositionHang("1", clip));
            Thread t2 = new Thread(new SetPositionHang("2", clip));
            Thread t3 = new Thread(new SetPositionHang("3", clip));
            Thread t4 = new Thread(new SetPositionHang("4", clip));
            Thread t5 = new Thread(new SetPositionHang("5", clip));
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (LineUnavailableException | IllegalArgumentException ignored) {
            // the test is not applicable
        }
    }

    public void run() {
        System.out.println("Thread " + thread + " Start");
        for (int i = 0; i < 100; i++) {
//            System.out.println("Thread " + thread + " Play " +
//                               System.currentTimeMillis() % 100000);
            playSound();
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Thread " + thread + " Finish");
    }

    private void playSound() {
        if (clip.isRunning()) {
            clip.stop();
        }
        if (testFramePosition) {
            clip.setFramePosition(0);
        } else {
            clip.setMicrosecondPosition(0);
        }
        clip.start();
    }
}
