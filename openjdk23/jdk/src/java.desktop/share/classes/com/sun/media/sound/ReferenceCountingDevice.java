/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.media.sound;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/** MidiDevice that can use reference counting for open/close.
 * This interface is intended to be used by MidiSystem.getTransmitter() and
 * MidiSystem.getReceiver().
 *
 * @author Matthias Pfisterer
 */
public interface ReferenceCountingDevice {
    /** Retrieve a Receiver that opens the device implicitly.
     * This method is similar to MidiDevice.getReceiver(). However, by calling this one,
     * the device is opened implicitly. This is needed by MidiSystem.getReceiver().
     */
    Receiver getReceiverReferenceCounting() throws MidiUnavailableException;

    /** Retrieve a Transmitter that opens the device implicitly.
     * This method is similar to MidiDevice.getTransmitter(). However, by calling this one,
     * the device is opened implicitly. This is needed by MidiSystem.getTransmitter().
     */
    Transmitter getTransmitterReferenceCounting() throws MidiUnavailableException;
}
