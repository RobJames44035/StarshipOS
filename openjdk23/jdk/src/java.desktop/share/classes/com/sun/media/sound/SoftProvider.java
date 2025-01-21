/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package com.sun.media.sound;

import java.util.Objects;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.spi.MidiDeviceProvider;

/**
 * Software synthesizer provider class.
 *
 * @author Karl Helgason
 */
public final class SoftProvider extends MidiDeviceProvider {

    @Override
    public MidiDevice.Info[] getDeviceInfo() {
        return new MidiDevice.Info[]{SoftSynthesizer.info};
    }

    @Override
    public MidiDevice getDevice(final MidiDevice.Info info) {
        Objects.requireNonNull(info);
        if (SoftSynthesizer.info.equals(info)) {
            return new SoftSynthesizer();
        }
        throw MidiUtils.unsupportedDevice(info);
    }
}
