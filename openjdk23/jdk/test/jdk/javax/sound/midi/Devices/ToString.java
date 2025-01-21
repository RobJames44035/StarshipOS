/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import javax.sound.midi.MidiDevice;

/**
 * @test
 * @bug 8236980
 */
public final class ToString {

    public static void main(String[] args) {
        MidiDevice.Info info = new MidiDevice.Info("nameToTest", "", "", ""){};
        if (!info.toString().contains("nameToTest")) {
            throw new RuntimeException("wrong string: " + info);
        }
    }
}
