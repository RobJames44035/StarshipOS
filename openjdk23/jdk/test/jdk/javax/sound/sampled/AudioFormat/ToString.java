/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import javax.sound.sampled.AudioFormat;

/**
 * @test
 * @bug 8236980
 */
public final class ToString {

    public static void main(String[] args) {
        AudioFormat.Encoding enc = new AudioFormat.Encoding("nameToTest") {};
        if (!enc.toString().equals("nameToTest")) {
            throw new RuntimeException("wrong string: " + enc);
        }
    }
}
