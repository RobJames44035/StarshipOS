/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftSynthesizer implicit open/close using getReceiver.
   @modules java.desktop/com.sun.media.sound:+open
*/

import java.lang.reflect.Field;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.*;
import javax.sound.midi.MidiDevice.Info;

import com.sun.media.sound.*;

public class ImplicitOpenClose {

    public static void main(String[] args) throws Exception {
        Field f = SoftSynthesizer.class.getDeclaredField("testline");
        f.setAccessible(true);
        f.set(null, new DummySourceDataLine());

        Synthesizer synth = new SoftSynthesizer();

        ReferenceCountingDevice rcd = (ReferenceCountingDevice)synth;

        // Test single open/close cycle

        Receiver recv = rcd.getReceiverReferenceCounting();
        if(!synth.isOpen())
            throw new Exception("Synthesizer not open!");
        recv.close();
        if(synth.isOpen())
            throw new Exception("Synthesizer not closed!");

        // Test using 2 receiver cycle

        Receiver recv1 = rcd.getReceiverReferenceCounting();
        if(!synth.isOpen())
            throw new Exception("Synthesizer not open!");
        Receiver recv2 = rcd.getReceiverReferenceCounting();
        if(!synth.isOpen())
            throw new Exception("Synthesizer not open!");

        recv2.close();
        if(!synth.isOpen())
            throw new Exception("Synthesizer was closed!");
        recv1.close();
        if(synth.isOpen())
            throw new Exception("Synthesizer not closed!");

        // Test for explicit,implicit conflict

        synth.open();
        Receiver recv3 = rcd.getReceiverReferenceCounting();
        if(!synth.isOpen())
            throw new Exception("Synthesizer not open!");
        recv3.close();
        if(!synth.isOpen())
            throw new Exception("Synthesizer was closed!");
        synth.close();
        if(synth.isOpen())
            throw new Exception("Synthesizer not closed!");

        // Test for implicit,explicit conflict

        recv3 = rcd.getReceiverReferenceCounting();
        synth.open();
        if(!synth.isOpen())
            throw new Exception("Synthesizer not open!");
        recv3.close();
        if(!synth.isOpen())
            throw new Exception("Synthesizer was closed!");
        synth.close();
        if(synth.isOpen())
            throw new Exception("Synthesizer not closed!");

    }
}
