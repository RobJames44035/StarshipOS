/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftSynthesizer getReceiver method
   @modules java.desktop/com.sun.media.sound
*/

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.sampled.*;
import javax.sound.midi.MidiDevice.Info;

import com.sun.media.sound.*;

public class GetReceiver {

    private static void assertEquals(Object a, Object b) throws Exception
    {
        if(!a.equals(b))
            throw new RuntimeException("assertEquals fails!");
    }

    private static void assertTrue(boolean value) throws Exception
    {
        if(!value)
            throw new RuntimeException("assertTrue fails!");
    }

    public static void main(String[] args) throws Exception {
        AudioSynthesizer synth = new SoftSynthesizer();
        synth.open(new DummySourceDataLine(), null);
        Receiver recv = synth.getReceiver();
        assertTrue(recv != null);
        Receiver recv2 = synth.getReceiver();
        assertTrue(recv2 != null);
        assertTrue(recv2 != recv);
        synth.close();

    }
}
